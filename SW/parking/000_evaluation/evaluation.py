import os
import sys
import numpy as np
from stable_baselines3 import SAC
import gymnasium as gym
import highway_env
import torch
import imageio

def evaluate(env, device):
    # Load saved model
    model = SAC.load('her_sac_highway', env=env, device=device)

    # We use the gym >v.26 API here. Note that you could also wrap the env in a DummyVecEnv
    # which allows you to use a simplified API
    obs, _ = env.reset()

    # Evaluate the agent and collect images for visualization
    episode_reward = 0
    images = []
    for _ in range(1000):
        action, _ = model.predict(obs, deterministic=True)
        obs, reward, terminated, truncated, info = env.step(action)
        done = truncated or terminated
        episode_reward += reward
        img = env.render()  # Collect images
        if img is not None:
            images.append(img)
        if done or info.get("is_success", False):
            print("Reward:", episode_reward, "Success?", info.get("is_success", False))
            episode_reward = 0.0
            obs, _ = env.reset()

    # Ensure the output directory exists
    output_dir = "output"
    os.makedirs(output_dir, exist_ok=True)

    # Save as gif
    if images:
        gif_path = os.path.join(output_dir, 'parking_simulation.gif')
        imageio.mimsave(gif_path, [np.array(img) for i, img in enumerate(images) if i % 2 == 0], fps=10)
        print(f"GIF saved at {gif_path}")
    else:
        print("No images to save.")

    # Example of accessing compute_reward using unwrapped or get_wrapper_attr
    # Fetch the reward weights from the environment's configuration
    reward_weights = env.unwrapped.config["reward_weights"]
    num_features = len(reward_weights)

    # Create dummy example values with the correct size
    achieved_goal = np.zeros(num_features)
    desired_goal = np.zeros(num_features)
    info = {}  # Replace with actual information as needed

    # Calculate reward
    reward = env.unwrapped.compute_reward(achieved_goal, desired_goal, info)    
    # 또는
    # reward = env.get_wrapper_attr('compute_reward')(achieved_goal, desired_goal, info)

if __name__ == "__main__":
    # Check if CUDA is available and set the device to GPU
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    if device.type == "cpu":
        print("CUDA is not available. Exiting the program.")
        sys.exit(1)

    print(f"Using device: {device}")

    env = gym.make("parking-v0", render_mode="rgb_array")

    evaluate(env, device)
