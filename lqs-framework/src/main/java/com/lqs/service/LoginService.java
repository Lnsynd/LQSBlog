package com.lqs.service;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.User;
import com.lqs.domain.vo.AdminUserInfoVo;
import com.lqs.domain.vo.RoutersVo;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult<AdminUserInfoVo> getInfo();

    ResponseResult<RoutersVo> getRouters();

    ResponseResult logout();
}
