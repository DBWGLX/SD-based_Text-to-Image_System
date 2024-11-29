package com.back.backregister.Service;

import com.back.backregister.pojo.User;

public interface UserService {
    public Integer register(User user);

    void sendEmail(String email);

    
}
