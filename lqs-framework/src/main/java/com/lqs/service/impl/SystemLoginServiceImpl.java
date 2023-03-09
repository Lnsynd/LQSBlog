package com.lqs.service.impl;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.LoginUser;
import com.lqs.domain.entity.User;
import com.lqs.domain.vo.AdminUserInfoVo;
import com.lqs.domain.vo.MenuVo;
import com.lqs.domain.vo.RoutersVo;
import com.lqs.domain.vo.UserInfoVo;
import com.lqs.service.LoginService;
import com.lqs.service.MenuService;
import com.lqs.service.RoleService;
import com.lqs.utils.BeanCopyUtils;
import com.lqs.utils.JwtUtil;
import com.lqs.utils.RedisCache;
import com.lqs.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SystemLoginServiceImpl implements LoginService {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;


    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());


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
        redisCache.setCacheObject("login:" + id, loginUser);

        //把token封装 返回
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult<AdminUserInfoVo> getInfo() {
        // 获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();

        // 根据id查询用户的权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());

        // 根据id查询用户的角色信息
        List<String> roles = roleService.selectRolesByUserId(loginUser.getUser().getId());


        // 获取用户信息
        User user = loginUser.getUser();
        // 封装
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roles, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @Override
    public ResponseResult<RoutersVo> getRouters() {
        // 查询结果menu  tree的形式返回
        // 获取当前登录的用户
        Long userId = SecurityUtils.getUserId();

        List<MenuVo> menus = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    @Override
    public ResponseResult logout() {
        // 获取当前登录的用户
        Long userId = SecurityUtils.getUserId();


        // 根据userId删除redis数据
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }


}
