package com.lishizhan.common.service;

import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.domain.entity.User;

/**
 * @Author : lishizhan
 * @Date : 2022/8/4/0004
 * @email : 1575234570@qq.com
 * @Description :
 */
public interface LoginService {
    /**
     * 前台用户登陆
     * @param user
     * @return
     */
    ResponseResult login(User user);
}
