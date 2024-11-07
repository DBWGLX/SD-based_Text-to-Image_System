# **/二次编辑加工**

*Contributed By* YongNan Zhou

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
