package com.back.web;

import com.back.core.Result;
import com.back.core.ResultGenerator;
import com.back.model.Admins;
import com.back.model.vo.User;
import com.back.service.AdminsService;
import com.back.utils.TokenUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AdminsService adminsService;

    @PostMapping("/login")
    public Result login(@RequestParam(required = true) String username,
                        @RequestParam(required = true) String password) {
        User user = null;
        Admins admins = adminsService.findByUsername(username);
        if (admins != null) {
            if (!admins.getPassword().equals(password)) {
                return ResultGenerator.genFailResult("用户名或密码错误");
            }
            user = new User();
            user.setId(admins.getId());
            user.setUsername(admins.getUsername());
        }
        if (user == null) {
            return ResultGenerator.genFailResult("用户不存在");
        } else {
            Map<String, Object> map = new HashMap<>();
            try {
                map.put("token", TokenUtil.createToken(user.getId() + ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResultGenerator.genSuccessResult(map).setMessage("登录成功");
        }
    }
}
