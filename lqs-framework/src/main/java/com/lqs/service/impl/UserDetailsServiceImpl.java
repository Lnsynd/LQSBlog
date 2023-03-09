package com.lqs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.lqs.constants.SystemConstants;
import com.lqs.domain.entity.LoginUser;
import com.lqs.domain.entity.Menu;
import com.lqs.domain.entity.User;
import com.lqs.mapper.MenuMapper;
import com.lqs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,s);

        User user = userMapper.selectOne(queryWrapper);

        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //  返回用户信息
        //  TODO 查询权限信息
        // TODO 后台用户才需要查询权限
        if(user.getType().equals(SystemConstants.ADMIN)){
            List<String> perms = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,perms);
        }

        return new LoginUser(user,null);
    }
}
