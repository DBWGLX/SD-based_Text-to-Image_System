![image](https://github.com/user-attachments/assets/b38b993e-7ea1-413a-925c-1c95f26e4047)# **/二次编辑加工**

*Contributed By* YongNan Zhou

这是一个springboot的图像编辑服务，用于接收图像编辑请求，处理图像，并将编辑后的图像保存和返回

前后端的交互：
1.后端定义了一个API接口 /api/edit/image，接收一个 ImageEditRequest 对象，包含图像路径和编辑参数

![image](https://github.com/user-attachments/assets/3411afea-10ab-4c0d-9d42-3096b2fd3fcf)

2.前端需要发送一个包含图像编辑请求的HTTP POST请求到后端的 /api/edit/image 接口，用axios库发送请求

3.由于图像文件通常较大，一般需要使用 FormData 来构建请求体

![image](https://github.com/user-attachments/assets/8850b046-e62c-4a99-87ec-c398ce7834af)

我自己在测试的时候，用的是raw的json传参

![image](https://github.com/user-attachments/assets/e682dd21-03d7-4117-aab0-604263ea42c2)



4.最后  后端返回的响应中包含编辑后的图像URL，前端可以使用这个URL来展示编辑之后的图像

前端img中src设置"http://localhost:8080/api/image/edited"可以直接展示图片比如<img src="http://localhost:8080/api/image/edited" alt="Image">也可以直接控制浏览器下载图片

比如window.open('http://localhost:8080/api/image/edited','_blank');

![image](https://github.com/user-attachments/assets/0f3a4b7a-4abd-4059-9ec7-16f7b560a919)




内容:

用了post请求

api："/api/edit/image"

三层架构解耦的方式调用服务层service的方法

```
应用亮度调整
应用对比度调整
更新像素值
```

具体实现看service包

我感觉这块跟方的色素提取差不多.....

利用postman工具传参
json格式写入图片的url路径
编辑自己设置数据，加工后的图片会自动保存在项目的根目录里
![image](https://github.com/user-attachments/assets/8cdc6963-9ed6-4413-bbe5-28b2aa7264f2)
这是对比图，我理解的二次编辑加工就是改变亮度、对比度、像素
对于yaxian哥在群里发的二次编辑视频，暂时还不会，后面再参考些模型学习改动吧

![image](https://github.com/user-attachments/assets/a1f6c57d-e462-435d-b878-1df9e86ef6df)
