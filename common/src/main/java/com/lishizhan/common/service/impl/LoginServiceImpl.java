package com.lishizhan.common.service.impl;

import com.lishizhan.common.constants.RedisConstants;
import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.domain.entity.LoginUser;
import com.lishizhan.common.domain.entity.User;
import com.lishizhan.common.domain.vo.UserInfoVo;
import com.lishizhan.common.domain.vo.UserLoginVo;
import com.lishizhan.common.service.LoginService;
import com.lishizhan.common.utils.BeanCopyUtils;
import com.lishizhan.common.utils.JwtUtil;
import com.lishizhan.common.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * @Author : lishizhan
 * @Date : 2022/8/4/0004
 * @email : 1575234570@qq.com
 * @Description :
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;


    /**
     * 登陆接口
     * @param user
     * @return
     */
    @Override
    public ResponseResult login(User user) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (ObjectUtils.isEmpty(authenticate)) throw new UsernameNotFoundException("用户名或密码有误");

        //获取用户id生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String token = JwtUtil.createJWT(Long.toString(userId));
        //将用户信息存入redis
        redisCache.setCacheObject(RedisConstants.BLOG_LOGIN_KEY+userId,loginUser,RedisConstants.BLOG_LOGIN_KEY_TLL, TimeUnit.MINUTES);

        //将token和user封装返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        return ResponseResult.okResult(new UserLoginVo(token,userInfoVo));
    }
}
