package com.lqs.service.impl;

import com.lqs.utils.SecurityUtils;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {

    /**
     * 判断当前用户是否具有perm
     * @param perm
     * @return
     */
    public boolean hasPermission(String perm){
        // 如果是超级管理员 直接返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }
        // 否则 获取当前用户的perm列表  查询是否存在当前perm
        List<String> perms = SecurityUtils.getLoginUser().getPerms();
        return perms.contains(perm);

    }
}
