package com.back.backregister.Service;


import com.back.backregister.dto.EmailDto;
import com.back.backregister.dto.LoginDto;
import com.back.backregister.dto.RegisterDto;

public interface UserService {
    public String register(RegisterDto registerDto);

    String sendEmail(EmailDto emailDto);

    String login(LoginDto loginDto);
}
