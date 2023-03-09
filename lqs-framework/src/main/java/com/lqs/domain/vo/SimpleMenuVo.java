package com.lqs.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMenuVo {
    //菜单ID
    private Long id;
    //菜单名称
    private String label;
    //父菜单ID
    private Long parentId;


    private List<? extends SimpleMenuVo> children;
}
