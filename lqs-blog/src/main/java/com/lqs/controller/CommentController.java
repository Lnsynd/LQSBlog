package com.lqs.controller;

import com.lqs.constants.SystemConstants;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddCommentDto;
import com.lqs.domain.entity.Comment;
import com.lqs.service.CommentService;
import com.lqs.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论",description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;


    // 查询文章评论
    @GetMapping("/commentList")
    @ApiOperation(value = "查询文章评论",notes = "获取一页文章评论")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
         return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }


    // 查询友链评论
    @GetMapping("/linkCommentList")
    @ApiOperation(value = "查询友链评论",notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    })
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }


    // 文章发表评论
    @PostMapping
    @ApiOperation(value = "发表评论")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }

}
