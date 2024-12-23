import requests

data = {
    "prompt": "a漂亮的女孩",
    "num_inference_steps": 50,
    "width": 512,
    "height": 512
}

response = requests.post("http://localhost:5001", json=data)
print("?")
print(response.json())