package com.lqs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-02-24 17:09:27
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

