package com.lishizhan.common.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author : lishizhan
 * @Date : 2022/8/4/0004
 * @email : 1575234570@qq.com
 * @Description : 登陆返回的VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo {

    private String token;
    private UserInfoVo userInfoVo;

}
