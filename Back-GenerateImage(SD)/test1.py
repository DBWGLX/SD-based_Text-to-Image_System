from flask import Flask, request, jsonify, send_file
from flask_cors import CORS
from diffusers import StableDiffusionPipeline
from datetime import datetime
# from diffusers import PNDMScheduler, DDIMScheduler, DPMSolverMultistepScheduler, LMSDiscreteScheduler, PNDMScheduler, EulerDiscreteScheduler
import torch
import os
import random


app = Flask(__name__)
CORS(app)  # 允许所有跨域请求

# 加载模型
pipe = StableDiffusionPipeline.from_single_file(
    "F:\\2024\\coding\\safetensors\\CheckpointYesmix_v50.safetensors",
    torch_dtype=torch.float16,
    local_files_only=True
)

# 设置 GPU 加速（如果有 GPU）
device = "cuda" if torch.cuda.is_available() else "cpu"
pipe = pipe.to(device)

@app.route('/generate', methods=['POST'])
def generate_image():
    # 获取请求中的数据
    data = request.json

    # 描述
    prompt = data.get("prompt", "a beautiful cool girl")
    negative_prompt = data.get("negative_prompt", "a beautiful cool girl")
    num_inference_steps = data.get("num_inference_steps", 30)

    # 尺寸
    width = data.get("width", 512)
    height = data.get("height", 512)

    # 设置自定义的调度器 (采样方法)
    # pipe.scheduler = PNDMScheduler.from_config(pipe.scheduler.config)
    # 1. DDIM 采样方法 【速度快】
    # DDIMScheduler.from_config(pipe.scheduler.config)
    # 2. DPM Solver 采样方法 【细节】
    # DPMSolverMultistepScheduler.from_config(pipe.scheduler.config)
    # 3. LMS Discrete Scheduler (Laplacian Pyramid Sampling) 【平滑】
    # LMSDiscreteScheduler.from_config(pipe.scheduler.config)
    # 4. PNDM Scheduler (Pseudo Numerical Methods) 【平衡】
    # PNDMScheduler.from_config(pipe.scheduler.config)
    # 5. Euler Discrete Scheduler (欧拉采样) 【速度快】
    # EulerDiscreteScheduler.from_config(pipe.scheduler.config)


    # 引导尺度(CFG) 7.5~15
    guidance_scale = data.get("guidance_scale", 7.5)
    # 随机种子，确保生成的图像可复现
    seed = data.get("seed",random.randint(0, 2**32 - 1))
    # 输出图像的类型: pil, tensor, numpy
    # output_type = data.get("output_type", "pil")
    # 自定义的潜在噪声
    # latents = data.get("latents", None)
    # 是否返回一个字典而不是简单的输出图像
    # return_dict = data.get("return_dict", True)

    # 生成图像 #
    # image = pipe(prompt, num_inference_steps=num_inference_steps, width=width, height=height).images[0]
    image = pipe(
        prompt=prompt,
        negative_prompt=negative_prompt,
        num_inference_steps=num_inference_steps,
        width=width,
        height=height,
        seed=seed,
        guidance_scale=7.5,
    ).images[0]



    # 获取当前日期和时间 => 图像名称
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    count = len(os.listdir("image")) + 1  # 计算当前目录下已有的图像数量
    image_path = f"image/{timestamp}_{count}.png"

    # 保存图像并返回路径
    image.save(image_path)
    
    return send_file(image_path, mimetype='image/png')

if __name__ == '__main__':
    # 创建保存图像的目录（如果不存在）
    if not os.path.exists("image"):
        os.makedirs("image")
    app.run(host='0.0.0.0', port=5000)
