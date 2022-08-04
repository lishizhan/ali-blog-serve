package com.lishizhan.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lishizhan.common.domain.entity.User;
import com.lishizhan.common.service.UserService;
import com.lishizhan.common.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Admin
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-08-04 16:49:00
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




