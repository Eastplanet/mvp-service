import sys
import gymnasium as gym
import highway_env
import numpy as np
import torch
from torch.nn import ReLU

from stable_baselines3 import HerReplayBuffer, SAC
from stable_baselines3.common.callbacks import BaseCallback
from stable_baselines3.common.vec_env import DummyVecEnv
from gymnasium.spaces import Box, Dict

# GPU 사용
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
if device.type == "cpu":
    print("CUDA is not available. Exiting the program.")
    sys.exit(1)

print(f"Using device: {device}")

# 환경 생성
config = {
    "observation": {
        # 목표지향형 운동 모델 설정
        "type": "KinematicsGoal",
        "features": ['x', 'y', 'vx', 'vy', 'cos_h', 'sin_h'],
        # 섬세함 높이는 스케일링
        "scales": [100, 100, 5, 5, 1, 1],
        "normalize": False
    },
    "action": {
        "type": "ContinuousAction"

        # 성공하면 해제할꺼 좀 더 정밀한 액션
        # ,"longitudinal": True,
        # "lateral": True
    },
    "simulation_frequency": 15,
    "policy_frequency": 5,
    "screen_width": 600,
    "screen_height": 300,
    "centering_position": [0.5, 0.5],
    "scaling": 7,
    "show_trajectories": False,
    "render_agent": True,
    "offscreen_rendering": False
}

# HERGoalEnvWrapper 클래스는 환경을 HER 알고리즘에 맞게 래핑하는 역할을 합니다.
class HERGoalEnvWrapper(gym.Wrapper):
    def __init__(self, env):
        super(HERGoalEnvWrapper, self).__init__(env)
        # 관찰 공간을 'observation', 'achieved_goal', 'desired_goal'로 나누어 설정합니다.
        self.observation_space = Dict({
            'observation': env.observation_space,  # 기존 환경의 관찰 공간
            'achieved_goal': Box(low=-np.inf, high=np.inf, shape=(2,), dtype=np.float32),  # 달성한 목표 공간
            'desired_goal': Box(low=-np.inf, high=np.inf, shape=(2,), dtype=np.float32)  # 원하는 목표 공간
        })
    
    # 환경을 초기화하고 초기 상태를 반환하는 메서드입니다.
    def reset(self, **kwargs):
        obs, info = self.env.reset(**kwargs)  # 원래 환경을 초기화하여 초기 관찰을 얻습니다.
        achieved_goal = obs[:2]  # 초기 관찰의 앞 두 요소를 달성한 목표로 설정합니다.
        desired_goal = np.zeros(2, dtype=np.float32)  # 원하는 목표는 (0, 0)으로 초기화합니다.
        return {
            'observation': obs,  # 현재 관찰
            'achieved_goal': achieved_goal,  # 달성한 목표
            'desired_goal': desired_goal  # 원하는 목표
        }, info
    
    # 환경에서 주어진 행동을 수행하고 다음 상태와 보상, 종료 여부, 추가 정보를 반환하는 메서드입니다.
    def step(self, action):
        obs, reward, done, truncated, info = self.env.step(action)  # 원래 환경에서 행동을 수행하여 결과를 얻습니다.
        achieved_goal = obs[:2]  # 관찰의 앞 두 요소를 달성한 목표로 설정합니다.
        desired_goal = np.zeros(2, dtype=np.float32)  # 원하는 목표는 (0, 0)으로 설정합니다.
        return {
            'observation': obs,  # 현재 관찰
            'achieved_goal': achieved_goal,  # 달성한 목표
            'desired_goal': desired_goal  # 원하는 목표
        }, reward, done, truncated, info  # 보상, 종료 여부, 추가 정보

env = gym.make("parking-v0", config=config)
env = HERGoalEnvWrapper(env)
env = DummyVecEnv([lambda: env])

# 콜백 함수
class SuccessRateCallback(BaseCallback):
    def __init__(self, verbose=0):
        super(SuccessRateCallback, self).__init__(verbose)
        self.success_count = 0
        self.episode_count = 0

    def _on_step(self) -> bool:
        done = self.locals.get('dones')
        info = self.locals.get('infos')

        if done and done[0]:
            self.episode_count += 1
            if info and info[0].get('is_success'):
                self.success_count += 1

            success_rate = (self.success_count / self.episode_count) * 100
            episode_rewards = self.locals.get('rewards')
            total_reward = np.sum(episode_rewards) if episode_rewards else 0
            print(f"Episode: {self.episode_count}, Reward: {total_reward}, Success Rate: {success_rate:.2f}%")

        return True

# SAC 하이퍼파라미터 설정
model = SAC(
    "MultiInputPolicy",
    env,
    replay_buffer_class=HerReplayBuffer,
    replay_buffer_kwargs=dict(
        n_sampled_goal=6,
        goal_selection_strategy="future",
    ),
    verbose=0,
    buffer_size=int(1e6),
    learning_rate=1e-3,
    gamma=0.99,
    batch_size=516,
    policy_kwargs=dict(net_arch=[256, 256, 256]),
    learning_starts=10000,
    device=device  
)

# 10만번 학습
model.learn(int(1e5), callback=SuccessRateCallback())

# Save the trained agent
model.save('her_sac_highway')
