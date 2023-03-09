package com.lqs.filter;

import com.alibaba.fastjson.JSON;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.LoginUser;
import com.lqs.enums.AppHttpCodeEnum;
import com.lqs.utils.JwtUtil;
import com.lqs.utils.RedisCache;
import com.lqs.utils.WebUtils;
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
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //  获取token
        String token = request.getHeader("token");

        // 如果没有token,说明不需要登录,放行
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }

        //获取userid
        Claims claims = null;

        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果token超时或者无效  返回
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response,JSON.toJSONString(result));
            return;
        }

        String userid = claims.getSubject();

        // redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userid);
        if(Objects.isNull(loginUser)){
            // 提示重新登陆
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response,JSON.toJSONString(result));
            return;
        }

        // 存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }


}
