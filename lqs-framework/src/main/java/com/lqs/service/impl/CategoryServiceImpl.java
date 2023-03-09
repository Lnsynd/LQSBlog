package com.lqs.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.constants.SystemConstants;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddCategoryDto;
import com.lqs.domain.entity.Article;
import com.lqs.domain.entity.Category;
import com.lqs.domain.vo.CategoryVo;
import com.lqs.domain.vo.ExcelCategoryVo;
import com.lqs.domain.vo.PageVo;
import com.lqs.enums.AppHttpCodeEnum;
import com.lqs.mapper.CategoryMapper;
import com.lqs.service.ArticleService;
import com.lqs.service.CategoryService;
import com.lqs.utils.BeanCopyUtils;
import com.lqs.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-02-21 14:34:35
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        // 查询有正式文章的列表
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleLambdaQueryWrapper);

        // 在列表中获取全部分类id
        // set去重
        Set<Long> categoryIds = articleList.stream().map(article -> article.getCategoryId()).collect(Collectors.toSet());

        // 分类id组成一个list
        List<Category> categories = listByIds(categoryIds);
        // 得到状态正常的分类
        categories = categories.stream().filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus())).collect(Collectors.toList());

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);

        List<Category> list = list(lambdaQueryWrapper);

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public void export(HttpServletResponse response) {
        // 设置请求头
        try {
            WebUtils.setDownLoadHeader("分类.xlsx", response);

            // 获取要导入的数据
            List<Category> categoryList = list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryList, ExcelCategoryVo.class);


            // 把数据写入到excel
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class)
                    .autoCloseStream(Boolean.FALSE)     // 自动关闭流
                    .sheet("分类导出")          // 设置Excel表格中工作簿的名称
                    .doWrite(excelCategoryVos);         // 要写入的数据
        } catch (Exception e) {
            // 出现错误返回json
            WebUtils.renderString(response,JSON.toJSONString(ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR)));
        }
    }

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param name   分类名
     * @param status  状态
     * @return
     */
    @Override
    public ResponseResult<PageVo> getCategoryPageList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(name), Category::getName, name);
        queryWrapper.eq(StringUtils.hasText(status), Category::getStatus, status);

        // 分页查询
        Page<Category> page = new Page<>(pageNum, pageSize);

        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(AddCategoryDto addCategoryDto) {
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public CategoryVo getCategory(Long id) {
        Category category = getById(id);
        return BeanCopyUtils.copyBean(category,CategoryVo.class);

    }


}

