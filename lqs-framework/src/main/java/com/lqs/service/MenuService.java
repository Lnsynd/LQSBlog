package com.lqs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.Menu;
import com.lqs.domain.vo.MenuKeyVo;
import com.lqs.domain.vo.MenuVo;
import com.lqs.domain.vo.SimpleMenuVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-03-02 16:51:28
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult menuList(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult getMenu(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long id);

    List<SimpleMenuVo> getMenuTree();

    ResponseResult<MenuKeyVo> getRoleMenuTree(Long id);
}

