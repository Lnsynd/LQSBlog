package com.lqs.domain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author lqs
 * @since 2023-03-07 21:31:06
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_role")
public class UserRole  {
    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;


}

