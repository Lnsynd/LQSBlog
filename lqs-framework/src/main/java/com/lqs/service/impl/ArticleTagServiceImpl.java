package com.lqs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.domain.entity.ArticleTag;
import com.lqs.mapper.ArticleTagMapper;
import com.lqs.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author lqs
 * @since 2023-03-04 21:29:52
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

