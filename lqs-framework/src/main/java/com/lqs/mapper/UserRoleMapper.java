package com.lqs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lqs.domain.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author lqs
 * @since 2023-03-07 21:31:05
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    void insertRoleByUserId(@Param("id") Long id, @Param("roleIds") List<Long> roleIds);

    void deleteRoleByUser(Long id);
}

