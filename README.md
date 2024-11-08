# Diffusion-based Text-to-Image System

![f15645f05c872c7b9682b25231e2c86c](https://github.com/user-attachments/assets/64f161ef-9e77-4706-9317-9140a536e494)


## Participants &nbsp;

- YaXian Wang (lubenweiNBNBNBNB)
- RuiXing Zhou (pur1fying)
- ShunQi Fan (DOTPHTP)
- YuHao Liao (3373808338)
- JiaXing Lin (Lam-L)
- YiLin Xu (LLXX666)
- PaiFeng Lian (Lian314)
- ZhengChen Jin (qianmengovo)
- YuRan Fang (Rosewwwfr)
- YongNan Zhou (YyyNan)

## Project Structure

![image](https://github.com/user-attachments/assets/2133bb27-dd1f-4046-a5eb-0d0ba321bab8)

## Deployment Guidance

1. set the **pipe** and **port** based on FLASK in ``'/SD/test1.py'``
2. set the **ip** of fetch as your backend server's ip in ``'/FrontEnd/vue-project/src/components/AppInterface.vue'``
3. notice the CORS

## Interface document

### /Back-GenerateImage(SD)

接口描述: 文生图进程接受post返回生成图片

请求路径: /generate 

请求方式: post 

请求参数:

```
{
  "prompt"
  "negative_prompt"
  "num_inference_steps"
  "width"
  "height"
  "guidance_scale"
  "seed"
}
```

响应数据: 一张生成的png图片。
