package com.lqs.handler.security;

import com.alibaba.fastjson.JSON;
import com.lqs.domain.ResponseResult;
import com.lqs.enums.AppHttpCodeEnum;
import com.lqs.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();

        ResponseResult result = null;
        if(e instanceof InsufficientAuthenticationException){
            // 如果未登录(授权出错)
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else if(e instanceof BadCredentialsException){
            // 如果登录不通过(认证出错)
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());
        }else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权出错");
        }

        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
