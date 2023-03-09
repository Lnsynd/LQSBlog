package com.lqs.service.impl;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.LoginUser;
import com.lqs.domain.entity.User;
import com.lqs.domain.vo.BlogUserLoginVo;
import com.lqs.domain.vo.UserInfoVo;
import com.lqs.service.BLoginService;
import com.lqs.utils.BeanCopyUtils;
import com.lqs.utils.JwtUtil;
import com.lqs.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BLoginServiceImpl implements BLoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //  判断是否验证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }

        //  获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        // token jwt
        String jwt = JwtUtil.createJWT(id);


        // token存入redis
        redisCache.setCacheObject("bloglogin:" + id, loginUser);

        //  封装token和userInfo
        // 将user拷贝到userInfo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        // 从SecurityContextHolder获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 获取userId
        Long userId = loginUser.getUser().getId();

        redisCache.deleteObject("bloglogin" + userId);
        return ResponseResult.okResult();
    }
}
