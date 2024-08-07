import sys
import gymnasium as gym
import highway_env
import numpy as np
import torch
from torch.nn import ReLU
from stable_baselines3 import HerReplayBuffer, SAC
from stable_baselines3.common.callbacks import BaseCallback

#  GPU 사용
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
if device.type == "cpu":
    print("CUDA is not available. Exiting the program.")
    sys.exit(1)

print(f"Using device: {device}")

# 환경 설정 파일 생성
config = {
    "observation": {
        "type": "KinematicsGoal",
        "features": ['x', 'y', 'vx', 'vy', 'cos_h', 'sin_h'],
        "scales": [100, 100, 5, 5, 1, 1],
        "normalize": False
    },
    "action": {
        "type": "ContinuousAction"
    },
    "simulation_frequency": 15,
    "policy_frequency": 5,
    "screen_width": 600,
    "screen_height": 300,
    "centering_position": [0.5, 0.5],
    "scaling": 7,
    "show_trajectories": True,
    "render_agent": True,
    "offscreen_rendering": False,
    "parking_spots": 8  # 주차 공간 개수 설정
}

# 환경 생성 및 설정 로드
env = gym.make("parking-v0")

# 환경 설정 적용
for key, value in config.items():
    env.config[key] = value

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
            # Assuming success is stored in 'is_success' in info dictionary
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
        n_sampled_goal=4,
        goal_selection_strategy="future",
    ),
    verbose=0,
    buffer_size=int(1e6),
    learning_rate=1e-3,
    gamma=0.99,
    batch_size=516,
    policy_kwargs=dict(net_arch=[256, 256, 256], activation_fn=ReLU),
    learning_starts=10000,
    device=device  
)

# 10만번 학습(1e5~1e6)
model.learn(int(1e5), callback=SuccessRateCallback())

# Save the trained agent
model.save('her_sac_highway')
