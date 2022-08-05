package com.lishizhan.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lishizhan.common.domain.entity.LoginUser;
import com.lishizhan.common.domain.entity.User;
import com.lishizhan.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @Author : lishizhan
 * @Date : 2022/8/4/0004
 * @email : 1575234570@qq.com
 * @Description :
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //根据用户名查询用户信息
        User user = userService.getOne(queryWrapper.eq(User::getUserName, s));
        //用户是否存在
        if (ObjectUtils.isEmpty(user)) throw new UsernameNotFoundException("用户名或密码错误");
        //TODO 查询权限信息


        LoginUser loginUser = new LoginUser();
        loginUser.setUser(user);
        return loginUser;
    }
}
