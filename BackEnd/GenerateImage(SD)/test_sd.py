# 模块导入
from flask import Flask, request, jsonify, send_file, send_from_directory
from flask_cors import CORS
from diffusers import StableDiffusionPipeline
from datetime import datetime
# from diffusers import PNDMScheduler, DDIMScheduler, DPMSolverMultistepScheduler, LMSDiscreteScheduler, PNDMScheduler, EulerDiscreteScheduler
import torch
import os
import random
import requests
import jwt 
from jwt.exceptions import ExpiredSignatureError, InvalidTokenError


if torch.cuda.is_available():
    print(f"CUDA is available. Device: {torch.cuda.get_device_name(0)}")
else:
    print("CUDA is not available.")

# 设置 GPU 加速（如果有 GPU）
device = "cuda" if torch.cuda.is_available() else "cpu"

# 加载模型
pipe = StableDiffusionPipeline.from_single_file(
    "F:\\2024\\coding\\safetensors\\CheckpointYesmix_v50.safetensors",
    torch_dtype=torch.float16,
    local_files_only=True
).to(device)

# device = "cuda" if torch.cuda.is_available() else "cpu"
# pipeline = StableDiffusionPipeline.from_pretrained(
#     "model_name", 
#     torch_dtype=torch.float16
# ).to(device)


image = pipe(
        prompt="a girl",
        guidance_scale=7.5,
    ).images[0]


# 获取当前日期和时间 => 图像名称
timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
count = len(os.listdir("image")) + 1  # 计算当前目录下已有的图像数量
image_path = f"image/{timestamp}_{count}.png"

# 保存图像
image.save(image_path)
