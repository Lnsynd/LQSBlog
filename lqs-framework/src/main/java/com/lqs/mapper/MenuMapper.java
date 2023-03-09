package com.lqs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lqs.domain.entity.Menu;
import com.lqs.domain.vo.MenuVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-02 16:51:27
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectAllRoutersMenu();

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);
}

