# **/图像和历史记录的增删改查**

*Contributed By:*  Yilin Xu <br>

**测试配置：** 
mysql连接和application.yml的更改<br>
![image](https://github.com/user-attachments/assets/977b8e3b-49b1-4339-8e4f-b8020a317af9)
<br>
记得自己创建一个user来测试
![image](https://github.com/user-attachments/assets/b56efe54-35a0-42a2-8ab1-71d84f05ffef)

<br>

**登录操作测试jwt：** 
请求参数（user），返回jwt令牌，在后续需要用到，jwt工具在utils包下。<br>
![image](https://github.com/user-attachments/assets/ff3f4c1e-40bb-414d-975c-7ea35155beb9)
<br>

**图片和历史记录存储：**
请求参数（images）
![image](https://github.com/user-attachments/assets/b1b979e9-0649-4b94-beb1-31a4e8cec5b7)
![image](https://github.com/user-attachments/assets/997dab36-0233-44e1-a582-13886a455181)
<br>

**用户查看自己的历史记录：**
请求参数（token）也就是把前面的jwt令牌输入
![image](https://github.com/user-attachments/assets/a5ef5f6c-fc0c-452f-9c93-5444817eb3e7)
![image](https://github.com/user-attachments/assets/8dce7ab8-8ad1-4c74-bf09-f5db1f75b3c1)
<br>

**管理员查看所有历史记录：**
请求参数（）
![image](https://github.com/user-attachments/assets/93709b15-2009-4f9d-a8da-c873b4640ef8)
<br>

**用户批量删除自己的历史记录：**
请求参数（token，historyIds）<br>
这里操作要循环，注意看resources下的com.llxx.mapper的xml配置文件
![image](https://github.com/user-attachments/assets/c89034ac-bce9-44f9-b16a-afe55d05b66a)
![image](https://github.com/user-attachments/assets/511261e8-094f-4d4f-979e-811768d822f5)
![image](https://github.com/user-attachments/assets/9a44b391-e69f-40f2-acba-95dd1ce0394f)

<br><br>
**说明：**
这个是测试版，在前后端联调的时候，所有的token都可以删除，在添加请求 ，获取请求头即可替代。












