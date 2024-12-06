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

# 创建 Flask 应用
app = Flask(__name__)
CORS(app)  # 允许所有跨域请求

# -1.配置静态文件夹 【# 设置图片文件夹路径】
app.config['IMAGE_FOLDER'] = 'image'  
# 路由：提供访问存储的图片
@app.route('/image/<filename>', methods=['GET'])
def get_image(filename):
    try:
        # 使用 send_from_directory 函数从 'image' 文件夹返回图片
        return send_from_directory(app.config['IMAGE_FOLDER'], filename)
    except FileNotFoundError:
        return jsonify({"error": "Image not found"}), 404



# 验证 JWT
sign_key = "text-to-image"
def is_valid_jwt(token):
    try:
        jwt.decode(token, sign_key, algorithms=["HS256"])
        return True
    except ExpiredSignatureError:
        return False
    except InvalidTokenError:
        return False


# 加载模型
pipe = StableDiffusionPipeline.from_single_file(
    "F:\\2024\\coding\\safetensors\\CheckpointYesmix_v50.safetensors",
    torch_dtype=torch.float16,
    local_files_only=True
)

# 设置 GPU 加速（如果有 GPU）
device = "cuda" if torch.cuda.is_available() else "cpu"
pipe = pipe.to(device)

# 定义路由
@app.route('/generate', methods=['POST'])
def generate_image():
    # 0.获取请求中的数据
    data = request.json

    # 1.验证token
    jwt_token = request.headers.get('Authorization')
    print(f"Token received: {jwt_token}")
    if not jwt_token or not is_valid_jwt(jwt_token):
        return jsonify({"error": "Invalid or expired JWT token"}), 403

    print("token verified")
    # 提取参数
    user_id = data.get("user_id", "default_user")
    # 描述
    prompt = data.get("prompt", "masterpiece, best quality,")
    negative_prompt = data.get("negative_prompt", "lowres, bad anatomy, bad hands, text, error, mssing fingers,extra digits, fewer digits, cropped, worst quality,low quality,normal quality,jpeg artifacts, signature, watermark,username, blurry")
    num_inference_steps = data.get("num_inference_steps", 30)

    # 2.翻译
    # translator = Translator(from_lang="en", to_lang="zh-CN")
    dataT = {
        "prompt": prompt
    }   
    urlT = "http://localhost:5001"
    try:
        response = requests.post(urlT, json=dataT)
        response.raise_for_status()  # 检查是否有 HTTP 错误
        # 提取返回的 JSON 数据
        response_json = response.json()
        if "prompt" in response_json:
            prompt = response_json["prompt"]  # 提取返回结果中的 "prompt" 字段
            print("翻译后的 prompt:", prompt)
        else:
            print("返回的 JSON 中没有 'prompt' 字段:", response_json)
    except requests.exceptions.RequestException as e:
        print("请求失败:", e)

    dataT = {
        "prompt": negative_prompt
    }   
    urlT = "http://localhost:5001"
    try:
        response = requests.post(urlT, json=dataT)
        response.raise_for_status()  # 检查是否有 HTTP 错误
        # 提取返回的 JSON 数据
        response_json = response.json()
        if "prompt" in response_json:
            negative_prompt = response_json["prompt"]  # 提取返回结果中的 "prompt" 字段
            print("翻译后的 negative_prompt:", negative_prompt)
        else:
            print("返回的 JSON 中没有 'negative_prompt' 字段:", response_json)
    except requests.exceptions.RequestException as e:
        print("请求失败2:", e)


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

    # 3.生成图像 #
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

    # 保存图像
    image.save(image_path)
    
    # 向历史记录进程发送生成数据
    # BACKEND_SERVER_URL = "http://127.0.0.1:5001/receive_image_details"
    # try:
    #     payload = {
    #         "user_id": user_id,
    #         "image_name": image_name,
    #         "prompt": prompt,
    #         "negative_prompt": negative_prompt,
    #         "num_inference_steps": num_inference_steps,
    #         "guidance_scale": guidance_scale,
    #         "width": width,
    #         "height": height,
    #         "seed": seed
    #     }
    #     response = requests.post(BACKEND_SERVER_URL, json=payload)
    #     if response.status_code == 200:
    #         print("图片生成详情已成功发送到后端")
    #     else:
    #         print(f"后端返回错误: {response.status_code}, {response.text}")
    # except Exception as e:
    #     print(f"向后端发送数据时发生错误: {e}")

    # 返回路径
    return send_file(image_path, mimetype='image/png')

# 启动 Flask 服务
if __name__ == '__main__':
    # 创建保存图像的目录（如果不存在）
    if not os.path.exists("image"):
        os.makedirs("image")
    app.run(host='0.0.0.0', port=5000)
