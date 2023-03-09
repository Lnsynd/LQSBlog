package com.lqs.domain.vo;

import com.lqs.domain.ResponseResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo extends ResponseResult {
    //角色ID
    private Long id;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;

    //备注
    private String remark;

    //删除标志（0代表存在 1代表删除）
    private String delFlag;
}
