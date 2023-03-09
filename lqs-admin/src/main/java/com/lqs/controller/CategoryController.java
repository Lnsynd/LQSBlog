package com.lqs.controller;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddCategoryDto;
import com.lqs.domain.entity.Category;
import com.lqs.domain.vo.CategoryVo;
import com.lqs.service.CategoryService;
import com.lqs.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
@Api(tags = "后台分类接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    @ApiOperation(value = "获取全部分类")
    public ResponseResult listAllCategory() {
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }


    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    @ApiOperation(value = "导出excel文件")
    public void export(HttpServletResponse response) {
        categoryService.export(response);
    }


    /**
     * 后台分页查询 分类列表
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String name, String status) {
        return categoryService.getCategoryPageList(pageNum, pageSize, name, status);
    }


    /**
     * 添加分类
     *
     * @param addCategoryDto
     * @return
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody AddCategoryDto addCategoryDto) {
        return categoryService.addCategory(addCategoryDto);
    }


    /**
     * 修改菜单接口
     * (1)回显菜单信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable Long id) {
        return ResponseResult.okResult(categoryService.getCategory(id));
    }


    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryVo categoryVo) {
        categoryService.updateById(BeanCopyUtils.copyBean(categoryVo, Category.class));
        return ResponseResult.okResult();
    }


    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id) {
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }


}
