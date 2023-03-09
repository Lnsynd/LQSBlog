package com.lqs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddCategoryDto;
import com.lqs.domain.entity.Category;
import com.lqs.domain.vo.CategoryVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-02-21 14:34:34
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();


    List<CategoryVo> listAllCategory();

    void export(HttpServletResponse response);


    ResponseResult getCategoryPageList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(AddCategoryDto addCategoryDto);

    CategoryVo getCategory(Long id);
}

