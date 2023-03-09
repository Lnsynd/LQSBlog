package com.lqs.domain.vo;

import com.lqs.domain.dto.BackUserDto;
import com.lqs.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackstageUserInfoVo {
    /**
     * 拥有的角色
     */
    private List<Long> roleIds;

    /**
     * 全部的角色列表
     */
    private List<Role> roles;
    private BackUserDto user;
}
