package com.lqs.domain.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author lqs
 * @since 2023-03-07 16:36:33
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu  {
    //角色ID
    private Long roleId;
    //菜单ID
    private Long menuId;


}

