# **/TOTP二次验证**

*Contributed By:*  Yilin Xu <br><br>

**测试准备工作：**
![image](https://github.com/user-attachments/assets/81fcae32-dfaf-4dc6-a74b-994ae40e0d44)
![image](https://github.com/user-attachments/assets/7bd5b067-eb45-44c8-b245-c002ee85a81a)

<br><br>
**测试流程：** <br>
**1.登录：**  
接口：/api/auth/login  -  请求参数：user
![image](https://github.com/user-attachments/assets/ca4a9a18-dbbf-40ae-b9f7-654d910e149e)
<br><br>
**2.发送验证码到邮箱：**
接口：/api/auth/totp/email  -  请求参数：token
![image](https://github.com/user-attachments/assets/0ba978b5-a5ba-4c41-a448-d230acb5bac1)
![image](https://github.com/user-attachments/assets/ea45d343-eea1-4950-bfb8-5ebb782d1687)
<br><br>
**3.二次验证：**
接口：/api/auth/totp/enable  -  请求参数：（email，验证码）<br>
注意：60秒超时，验证码失效，需要重新发。
![image](https://github.com/user-attachments/assets/bbd56aac-8132-4ddb-bad4-db1c173bd385)
<br><br><br><br>

**邮件发送技术分享：**
![image](https://github.com/user-attachments/assets/05ac35be-3a9f-4e1c-bf95-41a4026656b7)
![image](https://github.com/user-attachments/assets/e66dbdf1-977b-4fa7-9cd2-7a472b989482)
![image](https://github.com/user-attachments/assets/718d820c-407c-4784-8caf-dda4d58efbaa)
![image](https://github.com/user-attachments/assets/eea298b2-9e72-4466-a17e-e3bade4dba58)





















