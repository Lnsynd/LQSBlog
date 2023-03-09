package com.lqs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddArticleDto;
import com.lqs.domain.dto.ArticleListDto;
import com.lqs.domain.entity.Article;
import com.lqs.domain.vo.PageVo;


/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-02-20 17:42:51
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult<PageVo> systemArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    ResponseResult getArticle(Long id);

    ResponseResult updateArticle(AddArticleDto addArticleDto);

    ResponseResult deleteArticle(Long id);
}

