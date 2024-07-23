import sys
import gymnasium as gym
import highway_env
import numpy as np
import torch
from torch.nn import ReLU

from stable_baselines3 import HerReplayBuffer, SAC
from stable_baselines3.common.callbacks import BaseCallback
# from stable_baselines3.common.noise import NormalActionNoise


#  GPU 사용
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
if device.type == "cpu":
    print("CUDA is not available. Exiting the program.")
    sys.exit(1)

print(f"Using device: {device}")


# 환경 생성
env = gym.make("parking-v0")

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

    # 여러 입력을 받을 수 있는 정책
    "MultiInputPolicy",
    # 환경 사용하기
    env,
    # 리플레이 버퍼를 사용
    # 목표를 4번 샘플링 하고
    # goal_selection_strategy를 future로 설정
    replay_buffer_class=HerReplayBuffer,
    replay_buffer_kwargs=dict(
        n_sampled_goal=4,
        goal_selection_strategy="future",
    ),
    # 학습 과정 출력(0이면 최소화)
    verbose=0,
    # 버퍼 크기 100만으로 설정
    buffer_size=int(1e6),
    # 학습률을 0.001로 설정
    learning_rate=1e-3,
    # 감마 값 0.95->0.99
    gamma=0.99,
    # 배치 크기 256->516
    batch_size=516,
    # 정책 네트워크 아키텍처(활성화 함수로 레루 씀 activation_fn=ReLU 나중에 시험해봐야할듯)
    policy_kwargs=dict(net_arch=[256, 256, 256]),
    # 최소 스텝 충족 수 1만번
    learning_starts=10000,
    # GPU 써라
    device=device  
)

# 10만번 학습
model.learn(int(1e5), callback=SuccessRateCallback())

# Save the trained agent
model.save('her_sac_highway')
