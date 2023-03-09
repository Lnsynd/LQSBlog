package com.lqs.controller;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.User;
import com.lqs.domain.vo.AdminUserInfoVo;
import com.lqs.domain.vo.RoutersVo;
import com.lqs.enums.AppHttpCodeEnum;
import com.lqs.handler.exception.SystemException;
import com.lqs.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }


    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        return loginService.getInfo();
    }


    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        return loginService.getRouters();
    }


    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
