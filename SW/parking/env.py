import numpy as np
from abc import abstractmethod
from typing import Optional
from gymnasium import Env

from highway_env.envs.common.abstract import AbstractEnv
from highway_env.envs.common.observation import (
    MultiAgentObservation,
    observation_factory,
)
from highway_env.road.lane import LineType, StraightLane
from highway_env.road.road import Road, RoadNetwork
from highway_env.vehicle.graphics import VehicleGraphics
from highway_env.vehicle.kinematics import Vehicle
from highway_env.vehicle.objects import Landmark, Obstacle


# Clipping rewards(보상값을 일정 값으로 제한)
class GoalEnv(Env):
    @abstractmethod
    def compute_reward(
        self, achieved_goal: np.ndarray, desired_goal: np.ndarray, info: dict
    ) -> float:
        raise NotImplementedError
    """
    스텝 보상을 계산
        이 메서드는 보상 함수를 외부화하고 원하는 목표와 달성한 목표에 따라 보상을 계산
        목표와 무관한 추가 보상을 포함하려면, 'info'에 필요한 값을 포함하고 이를 기반으로 계산
    
    매개변수:
        achieved_goal (object): 실행 중에 달성한 목표
        desired_goal (object): 에이전트가 달성하려고 시도한 목표
        info (dict): 추가 정보를 담고 있는 정보 딕셔너리
        
    반환값:
        float: 제공된 달성한 목표와 원하는 목표에 해당하는 보상 값.
        다음 내용이 항상 참:
            ob, reward, done, info = env.step()
            assert reward == env.compute_reward(ob['achieved_goal'], ob['desired_goal'], info)
    """

class ParkingEnv(AbstractEnv, GoalEnv):
    PARKING_OBS = {
        "observation": {
            # 운동학적인 목표를 나타냄
            "type": "KinematicsGoal",
            "features": ["x", "y", "vx", "vy", "cos_h", "sin_h"],
            "scales": [100, 100, 5, 5, 1, 1],
            # 스케일 조정을 했기 때문에 정규화 X
            "normalize": False,
        }
    }
    # 시작 함수
    def __init__(self, config: dict = None, render_mode: Optional[str] = None) -> None:
        super().__init__(config, render_mode)
        self.observation_type_parking = None
        self.goal = None


    @classmethod
    def default_config(cls) -> dict:
        config = super().default_config()
        config.update(
            {
                "observation": {
                    "type": "KinematicsGoal",
                    "features": ["x", "y", "vx", "vy", "cos_h", "sin_h"],
                    "scales": [100, 100, 5, 5, 1, 1],
                    "normalize": False,
                },
                # 액션 공간
                "action": {"type": "ContinuousAction"},
                # 각 보상
                "reward_weights": [1, 0.3, 0, 0, 0.02, 0.02],
                "success_goal_reward": 0.12,
                "collision_reward": -5,
                "steering_range": np.deg2rad(20),
                "simulation_frequency": 20,  # 0.05초마다 시뮬레이션 상태 업데이트
                "policy_frequency": 10,      # 0.1초마다 에이전트의 행동 결정
                "duration": 100,
                "screen_width": 600,
                "screen_height": 300,
                "centering_position": [0.5, 0.5],
                # 스케일링
                "scaling": 7,
                "controlled_vehicles": 1,
                # 차량 전체 숫자 - 내가 움직일 차량 숫자
                "vehicles_count": 6,
                "add_walls": True,
                "max_speed": 20.0,
            }
        )
        return config

    # 공간 정의
    def define_spaces(self) -> None:
        super().define_spaces()
        self.observation_type_parking = observation_factory(
            self, self.PARKING_OBS["observation"]
        )

    def _info(self, obs, action) -> dict:
        info = super(ParkingEnv, self)._info(obs, action)
        if isinstance(self.observation_type, MultiAgentObservation):
            success = tuple(
                self._is_success(agent_obs["achieved_goal"], agent_obs["desired_goal"])
                for agent_obs in obs
            )
        else:
            obs = self.observation_type_parking.observe()
            success = self._is_success(obs["achieved_goal"], obs["desired_goal"])
        info.update({"is_success": success})
        return info

    def reset(self, seed: Optional[int] = None, options: Optional[dict] = None):
        super().reset(seed=seed)
        self._reset()
        observation = self.observation_type_parking.observe()
        info = {}  # 추가 정보를 빈 딕셔너리로 설정
        return observation, info

    def _reset(self):
        self._create_road()
        self._create_vehicles()

    def _create_road(self, spots: int = 4) -> None:
        # 도로 크기 설정
        net = RoadNetwork()
        width = 8.0
        lt = (LineType.CONTINUOUS, LineType.CONTINUOUS)
        x_offset = 0
        y_offset = 10
        length = 10.0

        for k in range(spots):
            x = (k + 1 - spots // 2) * (width + x_offset) - width / 2

            # 도로의 한쪽에 주차 공간 생성
            net.add_lane(
                "a",
                "b",
                StraightLane(
                    [x, y_offset], [x, y_offset + length], width=width, line_types=lt
                ),
            )
            net.add_lane(
                "b",
                "c",
                StraightLane(
                    [x, -y_offset], [x, -y_offset - length], width=width, line_types=lt
                ),
            )

            # 도로의 반대쪽에 동일한 주차 공간 생성
            net.add_lane(
                "a",
                "b",
                StraightLane(
                    [-x, y_offset], [-x, y_offset + length], width=width, line_types=lt
                ),
            )
            net.add_lane(
                "b",
                "c",
                StraightLane(
                    [-x, -y_offset], [-x, -y_offset - length], width=width, line_types=lt
                ),
            )

        self.road = Road(
            network=net,
            np_random=self.np_random,
            record_history=self.config["show_trajectories"],
        )


    def _create_vehicles(self) -> None:
        """Create some new random vehicles of a given type, and add them on the road."""
        empty_spots = list(range(len(self.road.network.lanes_list())))  # lanes_list의 인덱스를 사용

        # 제어 차량 설정
        self.controlled_vehicles = []
        for i in range(self.config["controlled_vehicles"]):
            # 차량의 초기 X좌표
            x0 = (i - self.config["controlled_vehicles"] // 2) * 10
            # 차량 생성
            vehicle = self.action_type.vehicle_class(
                self.road, [x0, 0], 2 * np.pi * self.np_random.uniform(), 0
            )
            vehicle.color = VehicleGraphics.EGO_COLOR

            # 차량 크기 설정
            # vehicle.LENGTH = 4.5  # 길이 설정 (예: 4.5미터)
            # vehicle.WIDTH = 2.0  # 너비 설정 (예: 2.0미터)

            # 차량 리스트에 추가
            self.road.vehicles.append(vehicle)
            self.controlled_vehicles.append(vehicle)
            if vehicle.lane_index in empty_spots:
                empty_spots.remove(vehicle.lane_index)

        # Goal 설정
        for vehicle in self.controlled_vehicles:
            # 무작위 빈 주차 공간 선택
            lane_index = empty_spots[self.np_random.choice(np.arange(len(empty_spots)))]
            # 선택된 차량 선 선택
            lane = self.road.network.lanes_list()[lane_index]
            # 주차 공간 중간에 목표 설정
            vehicle.goal = Landmark(
                self.road, lane.position(lane.length / 2, 0), heading=lane.heading
            )
            self.road.objects.append(vehicle.goal)
            empty_spots.remove(lane_index)

        # Set the goal attribute for the environment
        self.goal = self.controlled_vehicles[0].goal  # 첫 번째 차량의 goal을 환경의 goal로 설정

        # 모든 빈 주차 공간에 장애물 차량 추가
        for lane_index in empty_spots:
            # 올바른 형식으로 lane_index를 변환
            lane = self.road.network.lanes_list()[lane_index]
            position = lane.position(lane.length / 2, 0)  # 주차 공간의 중앙에 위치 설정
            
            # 주차 공간의 중앙에 이미 차량이 있는지 확인
            is_occupied = any(
                np.linalg.norm(np.array(vehicle.position) - np.array(position)) < vehicle.LENGTH
                for vehicle in self.road.vehicles
            )
            
            # goal 위치와 같은지 확인
            is_goal = any(
                np.linalg.norm(np.array(goal.position) - np.array(position)) < goal.LENGTH
                for goal in [v.goal for v in self.controlled_vehicles]
            )
            
            # 주차 공간이 비어 있고 goal 위치와 같지 않은 경우에만 장애물 차량 생성
            if not is_occupied and not is_goal:
                obstacle_vehicle = self.action_type.vehicle_class(
                    self.road, position, 0, 0
                )
                obstacle_vehicle.color = (255, 0, 0)
                obstacle_vehicle.heading = lane.heading
                self.road.vehicles.append(obstacle_vehicle)

        # 벽 만들기
        if self.config["add_walls"]:
            width, height = 70, 42
            for y in [-height / 2, height / 2]:
                obstacle = Obstacle(self.road, [0, y])
                obstacle.LENGTH, obstacle.WIDTH = (width, 1)
                obstacle.diagonal = np.sqrt(obstacle.LENGTH**2 + obstacle.WIDTH**2)
                self.road.objects.append(obstacle)
            for x in [-width / 2, width / 2]:
                obstacle = Obstacle(self.road, [x, 0], heading=np.pi / 2)
                obstacle.LENGTH, obstacle.WIDTH = (height, 1)
                obstacle.diagonal = np.sqrt(obstacle.LENGTH**2 + obstacle.WIDTH**2)
                self.road.objects.append(obstacle)



    def compute_reward(
        self,
        achieved_goal: np.ndarray,
        desired_goal: np.ndarray,
        info: dict,
        p: float = 0.5,
    ) -> float:
        return -np.power(
            np.dot(
                np.abs(achieved_goal - desired_goal),
                np.array(self.config["reward_weights"]),
            ),
            p,
        )

    def _reward(self, action: np.ndarray) -> float:
        obs = self.observation_type_parking.observe()
        obs = obs if isinstance(obs, tuple) else (obs,)
        reward = sum(
            self.compute_reward(
                agent_obs["achieved_goal"], agent_obs["desired_goal"], {}
            )
            for agent_obs in obs
        )
        reward += self.config["collision_reward"] * sum(
            v.crashed for v in self.controlled_vehicles
        )
        return reward

    def _is_success(self, achieved_goal: np.ndarray, desired_goal: np.ndarray) -> bool:
        return (
            self.compute_reward(achieved_goal, desired_goal, {})
            > -self.config["success_goal_reward"]
        )

    def _is_terminated(self) -> bool:
        crashed = any(vehicle.crashed for vehicle in self.controlled_vehicles)
        obs = self.observation_type_parking.observe()
        obs = obs if isinstance(obs, tuple) else (obs,)
        success = all(
            self._is_success(agent_obs["achieved_goal"], agent_obs["desired_goal"])
            for agent_obs in obs
        )
        return  bool(crashed or success)
    
    

    def _is_truncated(self) -> bool:
        return self.time >= self.config["duration"]

class ParkingEnvActionRepeat(ParkingEnv):
    def __init__(self):
        super().__init__({"policy_frequency": 1, "duration": 20})

class ParkingEnvParkedVehicles(ParkingEnv):
    def __init__(self):
        super().__init__()
