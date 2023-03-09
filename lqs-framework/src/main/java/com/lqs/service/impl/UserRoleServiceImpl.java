package com.lqs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.domain.entity.UserRole;
import com.lqs.mapper.UserRoleMapper;
import com.lqs.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author lqs
 * @since 2023-03-07 21:31:06
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

