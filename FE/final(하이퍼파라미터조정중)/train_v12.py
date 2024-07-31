import sys
import gymnasium as gym
import numpy as np
import torch
from torch.nn import ReLU
from stable_baselines3 import HerReplayBuffer, SAC
from stable_baselines3.common.callbacks import BaseCallback
from gymnasium.envs.registration import register
import matplotlib.pyplot as plt
import pickle
from tqdm import tqdm

# GPU 사용
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
if device.type == "cpu":
    print("CUDA is not available. Exiting the program.")
    sys.exit(1)

print(f"Using device: {device}")

# 환경 생성 및 설정 로드
register(
    id='CustomParkingEnv_Kimura_v12',
    entry_point='env:ParkingEnv',  # entry_point 설정
    max_episode_steps=1000, # 시뮬레이션 주파수와 에피소드 지속시간의 곱(duration)
)

# 환경 생성
env = gym.make("CustomParkingEnv_Kimura_v12")

# 콜백 함수
class SuccessRateCallback(BaseCallback):
    def __init__(self, verbose=0):
        super(SuccessRateCallback, self).__init__(verbose)
        self.success_count = 0
        self.episode_count = 0
        self.episode_rewards = []
        self.success_rates = []
        self.progress_bar = tqdm(total=int(2e6), desc="Training Progress")

    def _on_step(self) -> bool:
        done = self.locals.get('dones')[0]
        info = self.locals.get('infos')[0]

        if done:
            self.episode_count += 1
            total_reward = self.locals.get('rewards')[0]
            self.episode_rewards.append(total_reward)
            is_success = info.get('is_success')
            if is_success:
                self.success_count += 1

            # 에피소드 끝날 때마다 성공률 업데이트
            success_rate = (self.success_count / self.episode_count) * 100
            self.progress_bar.set_description(f"SuccessRate: {success_rate:.2f}%")

            # 성공률을 100 에피소드마다 저장
            if self.episode_count % 100 == 0:  # 100 에피소드마다 성공률 계산
                success_rate_last_100 = (self.success_count / 100) * 100
                self.success_rates.append(success_rate_last_100)
                self.success_count = 0  # 성공 카운트 초기화

        self.progress_bar.update(1)
        return True

    def _on_training_end(self) -> None:
        self.progress_bar.close()
        with open('episode_rewards.pkl', 'wb') as f:
            pickle.dump(self.episode_rewards, f)
        with open('success_rates.pkl', 'wb') as f:
            pickle.dump(self.success_rates, f)

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
    learning_rate= 1e-3,
    gamma=0.99,
    batch_size=516,
    policy_kwargs=dict(net_arch=[256, 256, 256], activation_fn=ReLU),
    learning_starts=10000,
    device=device  
)

# 10만번 학습(1e5~1e6)
model.learn(int(1000), callback=SuccessRateCallback())

# Save the trained agent
model.save('her_sac_highway')

# 보상 및 성공률 그래프 시각화
with open('episode_rewards.pkl', 'rb') as f:
    episode_rewards = pickle.load(f)

with open('success_rates.pkl', 'rb') as f:
    success_rates = pickle.load(f)

# 보상 그래프 그리기
plt.figure(figsize=(12, 6))
plt.subplot(2, 1, 1)
plt.plot(episode_rewards)
plt.xlabel('Episode')
plt.ylabel('Total Reward')
plt.title('Episode Rewards Over Time')

# 성공률 그래프 그리기
plt.subplot(2, 1, 2)
plt.plot(range(0, len(success_rates) * 100, 100), success_rates)
plt.xlabel('Episode')
plt.ylabel('Success Rate')
plt.title('Success Rate Over Time')

plt.tight_layout()
plt.show()
