package com.lqs.controller;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddRoleDto;
import com.lqs.domain.dto.RoleStatusDto;
import com.lqs.domain.entity.Role;
import com.lqs.domain.vo.PageVo;
import com.lqs.domain.vo.RoleVo;
import com.lqs.service.RoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 获取所有角色列表
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<PageVo> getRoleList(Integer pageNum,Integer pageSize,String roleName,String status){
        return roleService.getRoleList(pageNum,pageSize,roleName,status);
    }


    /**
     * 改变角色状态
     * @param addRoleDto
     * @return
     */
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleStatusDto addRoleDto){
        return roleService.changeStatus(addRoleDto);
    }


    /**
     * 后台
     * 添加角色
     * @return
     */
    @PostMapping
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto){
        return roleService.addRole(addRoleDto);
    }


    /**
     * 后台
     * 获取角色信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult<RoleVo> getRole(@PathVariable Long id){
        return roleService.getRole(id);
    }


    /**
     * 更新角色信息
     * @param addRoleDto
     * @return
     */
    @PutMapping
    public ResponseResult updateRole(@RequestBody AddRoleDto addRoleDto){
        return roleService.updateRole(addRoleDto);
    }



    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id){
        return roleService.deleteRole(id);
    }


    /**
     * 查询角色列表接口
     */
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }

}
