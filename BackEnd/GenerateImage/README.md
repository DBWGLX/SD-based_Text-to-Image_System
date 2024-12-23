# /Back-GenerateImage(SD)

> *Contributed By **YaXian Wang***

接受post请求，用diffusion库加载模型，生成图片，返回。

## 目录介绍：

### 0.requirements.txt

代码依赖（外部库）。

执行```pip install -r requirements.txt```来安装。

### 1.test1.py

这里是服务器代码。**需在pipe处加载你的模型。**

通过diffusion库，加载.safetensors模型，根据参数pipe出.png图片。

通过FLASK库，接受POST请求。

**POST** :&nbsp; /generate
```
{
  "prompt":
  "negative_prompt":
  "num_inference_steps":
  "width":
  "height":
  "guidance_scale":
  "seed":
}
```


### 2.test_post.py

一个post测试。

![image](https://github.com/user-attachments/assets/673d3967-e855-40b9-a5f9-39c56266da1a)

---

<br><br>



# getColor.py

> *Contributed By **YuRan Fang***

提取色素的RGB：

利用python的tkinter创建一个简单的程序，打开一个窗口，处理图片文件。（但是我不太知道应用场景是什么，不知道如何集成起来。）

接口 `ImageColorPicker(file_Path)` 就会打开一个窗口，点击某位置就能提示其rgb,(背景颜色就是其颜色)  这里如果点到白色就...（应该没人点白色吧）

![image-20241104154446332](https://raw.githubusercontent.com/Rosewwwfr/blog-imgs/main/blog/image-20241104154446332.png)

