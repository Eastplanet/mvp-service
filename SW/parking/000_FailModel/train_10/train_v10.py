import sys
import gymnasium as gym
import numpy as np
import torch
from torch.nn import ReLU
from stable_baselines3 import HerReplayBuffer, SAC
from stable_baselines3.common.callbacks import BaseCallback
from gymnasium.envs.registration import register

# GPU 사용
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
if device.type == "cpu":
    print("CUDA is not available. Exiting the program.")
    sys.exit(1)

print(f"Using device: {device}")

# 환경 생성 및 설정 로드
register(
    id='CustomParkingEnv_Kimura_v10',
    entry_point='env:ParkingEnv',  # entry_point 설정
    max_episode_steps=600, # 시뮬레이션 주파수와 에피소드 지속시간의 곱(duration)
)

# 환경 생성
env = gym.make("CustomParkingEnv_Kimura_v10")

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