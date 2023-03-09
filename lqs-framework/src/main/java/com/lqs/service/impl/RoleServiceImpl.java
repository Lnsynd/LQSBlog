package com.lqs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.constants.SystemConstants;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddRoleDto;
import com.lqs.domain.dto.RoleStatusDto;
import com.lqs.domain.entity.Role;
import com.lqs.domain.entity.User;
import com.lqs.domain.vo.PageVo;
import com.lqs.domain.vo.RoleVo;
import com.lqs.mapper.RoleMenuMapper;
import com.lqs.service.RoleService;
import com.lqs.mapper.RoleMapper;
import com.lqs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-02 16:56:09
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<String> selectRolesByUserId(Long id) {
        // 判断是否是管理员
        if(id == 1L){
            List<String> roleList = new ArrayList<>();
            roleList.add("admin");
            return roleList;
        }
        //  否则
        return getBaseMapper().selectRolesByUserId(id);
    }

    @Override
    public ResponseResult<PageVo> getRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        // 查询条件
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status), Role::getStatus, status);
        wrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName);


        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);


        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(RoleStatusDto addRoleDto) {

        Role role = getById(addRoleDto.getRoleId());
        role.setStatus(addRoleDto.getStatus());
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        // 存到role表中
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);

        // 为Role表和Menu表添加联系
        List<Long> menuIds = addRoleDto.getMenuIds();
        roleMenuMapper.insertRoleMenu(role.getId(),menuIds);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<RoleVo> getRole(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    public ResponseResult updateRole(AddRoleDto addRoleDto) {

        // 更新角色
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        updateById(role);

        // 更新角色权限
        List<Long> menuIds = addRoleDto.getMenuIds();
        // 如果不空，删除再插入
        if (menuIds != null && menuIds.size() > 0) {
            roleMenuMapper.deleteRoleMenuByRoleId(role.getId());
            roleMenuMapper.insertRoleMenu(role.getId(),menuIds);
        }else {
            // 直接删除全部权限
            roleMenuMapper.deleteRoleMenuByRoleId(role.getId());
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        // 删除角色表中的数据
        removeById(id);


        // 删除 角色权限表中的关联
        roleMenuMapper.deleteRoleMenuByRoleId(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);

        return ResponseResult.okResult(list(lambdaQueryWrapper));
    }


}

