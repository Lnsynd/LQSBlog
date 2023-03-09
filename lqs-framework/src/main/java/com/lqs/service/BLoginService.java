package com.lqs.service;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.User;

public interface BLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
