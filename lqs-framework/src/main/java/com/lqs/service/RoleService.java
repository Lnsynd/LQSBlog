package com.lqs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddRoleDto;
import com.lqs.domain.dto.RoleStatusDto;
import com.lqs.domain.entity.Role;
import com.lqs.domain.vo.PageVo;
import com.lqs.domain.vo.RoleVo;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-02 16:56:09
 */
public interface RoleService extends IService<Role> {

    List<String> selectRolesByUserId(Long id);

    ResponseResult<PageVo> getRoleList(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(RoleStatusDto addRoleDto);

    ResponseResult addRole(AddRoleDto addRoleDto);


    ResponseResult<RoleVo> getRole(Long id);

    ResponseResult updateRole(AddRoleDto addRoleDto);

    ResponseResult deleteRole(Long id);

    ResponseResult listAllRole();
}

