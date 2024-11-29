package com.back.backregister.Service;


import com.back.backregister.dto.EmailDto;
import com.back.backregister.dto.LoginDto;
import com.back.backregister.dto.RegisterDto;

public interface UserService {
    public Integer register(RegisterDto registerDto);

    void sendEmail(EmailDto emailDto);

    String login(LoginDto loginDto);
}
