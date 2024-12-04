![REPO SIZE](https://img.shields.io/github/repo-size/DBWGLX/SD-based_Text-to-Image_System.svg)
![CODE SIZE](https://img.shields.io/github/languages/code-size/DBWGLX/SD-based_Text-to-Image_System.svg)
![License](https://img.shields.io/github/license/DBWGLX/SD-based_Text-to-Image_System.svg)
![](https://img.shields.io/badge/%20史诗%20-8A2BE2)


# Diffusion-based Text-to-Image System

![f15645f05c872c7b9682b25231e2c86c](https://github.com/user-attachments/assets/64f161ef-9e77-4706-9317-9140a536e494)


## Project Structure

![image](https://github.com/user-attachments/assets/7c949bca-ce8c-40e5-b973-8bb69ecb7dec)


## Push Code Specifications 推送代码规范 (tmp ,New in 11.25)

![3ec5a727c3dce90abb930666922d13e](https://github.com/user-attachments/assets/0f71a70c-4462-4ffa-8aaf-72655780d7a1)

如图，加入自己的工作环境到这个隐藏目录。

**忽略任何路径下的 .vscode 和 .idea 文件夹（无论其位置），可以使用通配符：**

```
**/.vscode/
**/.idea/
```

> .git/info/exclude 是 Git 仓库的一个本地忽略规则文件，用于定义 当前仓库 的忽略规则。

> 与 .gitignore 的功能类似，但 .git/info/exclude 是仅对本地生效的，并不会被推送到远程仓库。


## Deployment Guidance

1. set the **pipe** and **port** based on FLASK in ``'/SD/test1.py'``
2. set the **ip** of fetch as your backend server's ip in ``'/FrontEnd/vue-project/src/components/AppInterface.vue'``
3. to run the FrontEnd , use ``http-server ./dist -c-1 --cors``  server in public (./dict is made by ``npm run built``)

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
