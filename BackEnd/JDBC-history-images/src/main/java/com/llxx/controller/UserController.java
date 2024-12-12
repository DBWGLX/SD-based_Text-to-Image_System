package com.llxx.controller;

import com.llxx.pojo.Result;
import com.llxx.pojo.Users;
import com.llxx.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/{userId}")
    public Result getUsers(@PathVariable Integer userId) {
        return usersService.getUserById(userId);
    }

}
