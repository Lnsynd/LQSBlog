package com.lqs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lqs.domain.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author lqs
 * @since 2023-03-07 16:36:33
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {


    void insertRoleMenu(@Param("id") Long id, @Param("menuIds") List<Long> menuIds);

    List<Long> getMenuIdByRoleId(Long id);

    void deleteRoleMenuByRoleId(Long id);
}

