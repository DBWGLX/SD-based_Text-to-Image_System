# /SD

> *Contributed By **YaXian Wang***

### 0.requirements.txt

执行```pip install -r requirements.txt```来安装此python代码执行所需依赖。

### 1.test1.py

这里是服务器代码。*From flask, diffusers, datetime*

若想运行，请在pipe处加载你的模型。

接受POST传JOSN参
'/generate'
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


### 2.test_post.py

发送post请求来测试。

此代码通过python的request库来发送post请求并储存返回的图片

![image](https://github.com/user-attachments/assets/673d3967-e855-40b9-a5f9-39c56266da1a)

---

<br><br>

![](C:\Users\86133\Desktop\24-25深大\岗位实践\Project\Myproject\imgs\test.png)



# getColor.py

> *Contributed By **YuRan Fang***

提取色素的RGB：

利用python的tkinter创建一个简单的程序，打开一个窗口，处理图片文件。（但是我不太知道应用场景是什么，不知道如何集成起来。）

接口 `ImageColorPicker(file_Path)` 就会打开一个窗口，点击某位置就能提示其rgb,(背景颜色就是其颜色)  这里如果点到白色就...（应该没人点白色吧）

![image-20241104154446332](https://raw.githubusercontent.com/Rosewwwfr/blog-imgs/main/blog/image-20241104154446332.png)

