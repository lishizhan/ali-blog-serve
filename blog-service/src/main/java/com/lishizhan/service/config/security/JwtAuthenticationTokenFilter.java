package com.lishizhan.service.config.security;

import com.alibaba.fastjson.JSON;
import com.lishizhan.common.constants.RedisConstants;
import com.lishizhan.common.constants.SystemConstants;
import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.domain.entity.LoginUser;
import com.lishizhan.common.enums.AppHttpCodeEnum;
import com.lishizhan.common.utils.JwtUtil;
import com.lishizhan.common.utils.RedisCache;
import com.lishizhan.common.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : lishizhan
 * @Date : 2022/8/4/0004
 * @email : 1575234570@qq.com
 * @Description :
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader(SystemConstants.JWT_TOKEN);
        if (!StringUtils.hasText(token)){
            //该接口不需要登陆直接放行
            chain.doFilter(request,response);
            return;
        }
        //解析token的用户ID
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //token超时，token非法
            e.printStackTrace();
            //响应给前端登陆
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response,JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        //从redis获取用户信息
        LoginUser loginUser = redisCache.getCacheObject(RedisConstants.BLOG_LOGIN_KEY + userId);
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }
}
