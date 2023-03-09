package com.lqs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lqs.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-02 16:56:07
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRolesByUserId(Long id);
}

