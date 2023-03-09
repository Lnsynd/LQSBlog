package com.lqs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mapper.RoleMenuMapper;
import com.lqs.service.RoleMenuService;
import com.lqs.domain.entity.RoleMenu;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author lqs
 * @since 2023-03-07 16:36:34
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

