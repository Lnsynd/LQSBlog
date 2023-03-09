package com.lqs.controller;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.User;
import com.lqs.enums.AppHttpCodeEnum;
import com.lqs.handler.exception.SystemException;
import com.lqs.service.BLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BLoginService bLoginService;


    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return bLoginService.login(user);
    }


    @PostMapping("/logout")
    public ResponseResult logout(){
        return bLoginService.logout();
    }
}
