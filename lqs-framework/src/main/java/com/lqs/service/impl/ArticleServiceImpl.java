package com.lqs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.constants.SystemConstants;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddArticleDto;
import com.lqs.domain.dto.ArticleListDto;
import com.lqs.domain.entity.Article;
import com.lqs.domain.entity.ArticleTag;
import com.lqs.domain.entity.Category;
import com.lqs.domain.entity.Tag;
import com.lqs.domain.vo.ArticleDetailVo;
import com.lqs.domain.vo.ArticleListVo;
import com.lqs.domain.vo.HotArticleVo;
import com.lqs.domain.vo.PageVo;
import com.lqs.mapper.ArticleMapper;
import com.lqs.service.ArticleService;
import com.lqs.service.ArticleTagService;
import com.lqs.service.CategoryService;
import com.lqs.utils.BeanCopyUtils;
import com.lqs.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        // 创建查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 要求正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多查询十条
        Page<Article> page = new Page<>(1, 10);
        // 查询
        page(page, queryWrapper);

        // 得到数据
        List<Article> articles = page.getRecords();

        List<HotArticleVo> vos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(vos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //  有categoryId就要查询和传入相同的
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //  正式发布的
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //  置顶的显示在前面(isTop降序排列)
        queryWrapper.orderByDesc(Article::getIsTop);
        //  分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        //  存入分类名称
        List<Article> articles = page.getRecords();

        for (Article article : articles) {
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());
        }

        //  封装
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 根据id查询文章
        Article article = getById(id);

        // 从redis获取浏览量
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());

        // 转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 查询分类名
        Long categoryId = article.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }


        return ResponseResult.okResult(articleDetailVo);
    }


    /**
     * 更新文章浏览量
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult updateViewCount(Long id) {
        // 从redis中获取浏览量数据
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult add(AddArticleDto addArticleDto) {

        //添加 博客
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);


        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<PageVo> systemArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        //创建查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(articleListDto.getTitle()), Article::getTitle, articleListDto.getTitle());
        wrapper.like(StringUtils.hasText(articleListDto.getSummary()), Article::getSummary, articleListDto.getSummary());


        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 后台获取文章信息
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticle(Long id) {
        // 获取文章信息
        Article article = getById(id);

        // 将标签信息存入
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, article.getId());

        List<ArticleTag> articleTags = articleTagService.list(wrapper);
        List<Long> tagList = articleTags.stream().map(tag -> tag.getTagId()
        ).collect(Collectors.toList());
        AddArticleDto addArticleDto = BeanCopyUtils.copyBean(article, AddArticleDto.class);
        addArticleDto.setTags(tagList);
        return ResponseResult.okResult(addArticleDto);
    }

    /**
     * 后台更新文章
     *
     * @param addArticleDto
     * @return
     */
    @Override
    public ResponseResult updateArticle(AddArticleDto addArticleDto) {
        // 更新文章
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        updateById(article);

        // 更新标签
        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, article.getId());
        articleTagService.remove(wrapper);
        articleTagService.saveBatch(articleTags);


        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        // Article表删除
        removeById(id);

        // ArticleTag删除
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, id);
        articleTagService.remove(wrapper);

        return ResponseResult.okResult();
    }
}
