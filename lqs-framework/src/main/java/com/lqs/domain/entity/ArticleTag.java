package com.lqs.domain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标签关联表(LqsArticleTag)表实体类
 *
 * @author lqs
 * @since 2023-03-04 21:21:29
 */
@SuppressWarnings("serial")
@TableName(value="lqs_article_tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag implements Serializable {
    private static final long serialVersionUID = 625337492348897098L;

    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 标签id
     */
    private Long tagId;



}

