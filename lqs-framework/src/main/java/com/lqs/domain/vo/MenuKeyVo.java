package com.lqs.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuKeyVo {
    private List<Long> checkedKeys;
    private List<SimpleMenuVo> menus;
}
