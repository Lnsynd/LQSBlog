package com.lqs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.constants.SystemConstants;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.Menu;
import com.lqs.domain.vo.MenuKeyVo;
import com.lqs.domain.vo.MenuVo;
import com.lqs.domain.vo.SimpleMenuVo;
import com.lqs.mapper.MenuMapper;
import com.lqs.mapper.RoleMenuMapper;
import com.lqs.service.MenuService;
import com.lqs.utils.BeanCopyUtils;
import com.lqs.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-02 16:51:28
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;


    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员返回所有权限
        if (id == 1L) {
            LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            lambdaQueryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);

            List<Menu> menus = list(lambdaQueryWrapper);

            List<String> perms = menus.stream().map(Menu::getPerms).collect(Collectors.toList());
            return perms;
        }

        // 如果不是
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<MenuVo> menuVos = null;
        // 查询是否管理员
        if (SecurityUtils.isAdmin()) {
            // 查找所有符合要求的menu
            menuVos = menuMapper.selectAllRoutersMenu();
        } else {
            // 查询当前用户拥有的menu
            menuVos = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建Tree
        // 找出第一层的菜单,将他们的子菜单设置到children
        return builderMenuTree(menuVos);
    }

    /**
     * 构建菜单Tree
     *
     * @param menuList 所有菜单列表
     * @return
     */
    private <T extends SimpleMenuVo> List<T> builderMenuTree(List<T> menuList) {
        return menuList.stream()
                // 过滤出第一级菜单
                .filter(menuVo -> menuVo.getParentId() == 0)
                // 为第一级菜单设置子菜单
                .peek(menuVo -> menuVo.setChildren(getChildren(menuVo.getId(), menuList)))
                .collect(Collectors.toList());
    }


    /**
     * 获取子菜单
     *
     * @param id    当前菜单的id
     * @param menuList 所有菜单名 列表
     * @param <T>
     * @return
     */
    private <T extends SimpleMenuVo> List<T> getChildren(Long id, List<T> menuList) {
        return menuList.stream()
                // 过滤出子菜单
                .filter(menu -> menu.getParentId().equals(id))
                .peek(childrenMenu -> childrenMenu.setChildren(getChildren(childrenMenu.getId(), menuList)))
                .collect(Collectors.toList());
    }

    /**
     * 后台
     * 获取菜单列表
     *
     * @param status
     * @param menuName
     * @return
     */
    @Override
    public ResponseResult menuList(String status, String menuName) {
        // 查询条件
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status), Menu::getStatus, status);
        wrapper.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);


        List<Menu> menus = list(wrapper);
        return ResponseResult.okResult(menus);
    }

    /**
     * 后台
     * 添加菜单
     *
     * @param menu
     * @return
     */
    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    /**
     * 后台
     * 获取单个菜单信息
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult getMenu(Long id) {
        Menu menu = getById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    /**
     * 后台
     * 更新菜单信息
     *
     * @param menu
     * @return
     */
    @Override
    public ResponseResult updateMenu(Menu menu) {
        // 若修改后的菜单信息将本菜单设置为父菜单 返回错误提示
        if (menu.getParentId().equals(SystemConstants.PARENT_MENU)) {
            return ResponseResult.errorResult(500, "修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    /**
     * 后台
     * 删除菜单信息
     *
     * @param menuId
     * @return
     */
    @Override
    public ResponseResult deleteMenu(Long menuId) {
        // 如果该菜单有子菜单则不能 删除
        // 查询 menu表中是否有  父级id为 该菜单的 menu
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, menuId);
        List<Menu> menuList = list(wrapper);
        if (!ObjectUtils.isEmpty(menuList)) {
            return ResponseResult.errorResult(500, "存在子菜单不允许删除");
        }
        removeById(menuId);
        return ResponseResult.okResult();
    }

    /**
     * 后台
     * 得到菜单列表
     * @return
     */
    @Override
    public List<SimpleMenuVo> getMenuTree() {
        List<Menu> menus = list();

        List<SimpleMenuVo> simpleMenuVos = menus.stream().map(menu -> {
            SimpleMenuVo simpleMenuVo = new SimpleMenuVo();
            simpleMenuVo.setId(menu.getId());
            simpleMenuVo.setLabel(menu.getMenuName());
            simpleMenuVo.setParentId(menu.getParentId());
            return simpleMenuVo;
        }).collect(Collectors.toList());

        return builderMenuTree(simpleMenuVos);
    }

    @Override
    public ResponseResult<MenuKeyVo> getRoleMenuTree(Long id) {
        //  查询菜单树
        List<SimpleMenuVo> menuTree = getMenuTree();

        // 查询该id拥有的权限
        List<Long> checkedKeys = roleMenuMapper.getMenuIdByRoleId(id);
        return ResponseResult.okResult(new MenuKeyVo(checkedKeys,menuTree));
    }


}

