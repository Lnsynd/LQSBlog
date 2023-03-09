package com.lqs.controller;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddUserDto;
import com.lqs.domain.entity.User;
import com.lqs.domain.vo.BackstageUserInfoVo;
import com.lqs.domain.vo.PageVo;
import com.lqs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 用户列表
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param phonenumber
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<PageVo> getList(Integer pageNum, Integer pageSize,String userName,String phonenumber,String status){
        return userService.getList(pageNum,pageSize,userName,phonenumber,status);
    }


    /**
     * 新增用户
     * @param addUserDto
     * @return
     */
    @PostMapping
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto){
        return userService.addUser(addUserDto);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }


    /**
     * 修改用户信息
     * （1）用户信息回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }


    /**
     * 修改用户信息
     * (2) 提交用户信息
     * @param addUserDto
     * @return
     */
    @PutMapping
    public ResponseResult updateUser(@RequestBody AddUserDto addUserDto){
        return userService.updateUser(addUserDto);
    }



}
