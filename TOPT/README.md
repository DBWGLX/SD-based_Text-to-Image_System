# **/TOTP二次验证**

*Contributed By:*  Yilin Xu <br><br>

**测试准备工作：**
![image](https://github.com/user-attachments/assets/81fcae32-dfaf-4dc6-a74b-994ae40e0d44)
![image](https://github.com/user-attachments/assets/9d6f8ad4-0a5c-4829-94e4-0241cb993f22)
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




















