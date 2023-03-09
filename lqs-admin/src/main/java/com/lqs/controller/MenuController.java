package com.lqs.controller;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.Menu;
import com.lqs.domain.vo.MenuKeyVo;
import com.lqs.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 后台获取菜单列表
     * @param status
     * @param menuName
     * @return
     */
    @GetMapping("/list")
    public ResponseResult menuList(String status,String menuName){
        return menuService.menuList(status,menuName);
    }


    /**
     * 添加菜单
     * @param menu
     * @return
     */
    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }


    /**
     * 获取单个菜单信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable Long id){
        return menuService.getMenu(id);
    }


    /**
     * 更新菜单信息
     * @param menu
     * @return
     */
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    /**
     * 删除菜单信息
     * @param menuId
     * @return
     */
    @DeleteMapping("/{menuId}")
    public ResponseResult updateMenu(@PathVariable Long menuId){
        return menuService.deleteMenu(menuId);
    }


    /**
     *  获取菜单树接口
     * @return
     */
    @GetMapping("/treeselect")
    public ResponseResult getMenuTree(){
        return ResponseResult.okResult(menuService.getMenuTree());
    }



    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult<MenuKeyVo> getRoleMenuTree(@PathVariable Long id){
        return menuService.getRoleMenuTree(id);
    }

}
