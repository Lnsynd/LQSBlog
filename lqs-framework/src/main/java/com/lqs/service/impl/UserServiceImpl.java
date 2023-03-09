package com.lqs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.constants.SystemConstants;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddUserDto;
import com.lqs.domain.dto.BackUserDto;
import com.lqs.domain.entity.Comment;
import com.lqs.domain.entity.Role;
import com.lqs.domain.entity.User;
import com.lqs.domain.entity.UserRole;
import com.lqs.domain.vo.BackstageUserInfoVo;
import com.lqs.domain.vo.PageVo;
import com.lqs.domain.vo.UserInfoVo;
import com.lqs.enums.AppHttpCodeEnum;
import com.lqs.handler.exception.SystemException;
import com.lqs.mapper.UserMapper;
import com.lqs.mapper.UserRoleMapper;
import com.lqs.service.RoleService;
import com.lqs.service.UserRoleService;
import com.lqs.service.UserService;
import com.lqs.utils.BeanCopyUtils;
import com.lqs.utils.SecurityUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-02-24 18:15:13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {
        // 通过token获取用户id
        Long userId = SecurityUtils.getUserId();

        // 通过id获取用户
        User user = getById(userId);

        // 封装
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }


    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    /**
     * 注册新用户
     *
     * @param user
     * @return
     */
    @Override
    public ResponseResult<T> register(User user) {
        // 对数据非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICENAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }

        // 判断数据是否重复
        if (usernameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        // 密码加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        // 存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<PageVo> getList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(userName), User::getUserName, userName);
        queryWrapper.like(StringUtils.hasText(phonenumber), User::getPhonenumber, phonenumber);
        queryWrapper.eq(StringUtils.hasText(status), User::getStatus, status);

        // 分页查询
        Page<User> page = new Page<>(pageNum, pageSize);

        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult<T> addUser(AddUserDto addUserDto) {
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        save(user);

        List<Long> roleIds = addUserDto.getRoleIds();
        userRoleMapper.insertRoleByUserId(user.getId(), roleIds);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        removeById(id);

        // 用户角色表
        userRoleMapper.deleteRoleByUser(id);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserById(Long id) {
        // 查询角色表
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoles = userRoleService.list(wrapper);
        List<Long> roleIds = userRoles.stream().map(userRole -> {
            return userRole.getRoleId();
        }).collect(Collectors.toList());


        // 查询全部角色列表
        List<Role> roles = roleService.list();

        // 查询用户信息
        User u = getById(id);
        BackUserDto user = BeanCopyUtils.copyBean(u, BackUserDto.class);

        return ResponseResult.okResult(new BackstageUserInfoVo(roleIds, roles, user));
    }

    /**
     * 后台更新用户信息
     *
     * @param addUserDto 增加的用户信息
     * @return
     */
    @Override
    public ResponseResult updateUser(AddUserDto addUserDto) {
        // 更新用户信息表
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        updateById(user);

        // 更新用户角色表
        List<Long> roleIds = addUserDto.getRoleIds();
        if (ObjectUtils.isEmpty(roleIds)) {
            userRoleMapper.deleteRoleByUser(user.getId());
        } else {
            userRoleMapper.deleteRoleByUser(user.getId());
            userRoleMapper.insertRoleByUserId(user.getId(), roleIds);
        }
        return ResponseResult.okResult();
    }


    // 判断是否存在重复邮箱
    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail, email);

        int count = count(lambdaQueryWrapper);
        return count > 0;
    }

    // 判断是否存在重复用户名
    private boolean usernameExist(String userName) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName, userName);

        int count = count(lambdaQueryWrapper);
        return count > 0;
    }
}

