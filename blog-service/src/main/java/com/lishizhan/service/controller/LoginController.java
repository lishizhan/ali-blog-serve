package com.lishizhan.service.controller;

import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.domain.entity.User;
import com.lishizhan.common.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : lishizhan
 * @Date : 2022/8/4/0004
 * @email : 1575234570@qq.com
 * @Description :
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }
}
