package com.lqs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddUserDto;
import com.lqs.domain.entity.User;
import com.lqs.domain.vo.BackstageUserInfoVo;
import com.lqs.domain.vo.PageVo;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-02-24 18:15:12
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult<PageVo> getList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(AddUserDto addUserDto);

    ResponseResult deleteUser(Long id);

    ResponseResult getUserById(Long id);

    ResponseResult updateUser(AddUserDto addUserDto);
}

