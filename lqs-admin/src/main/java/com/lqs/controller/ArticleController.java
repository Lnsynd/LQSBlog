package com.lqs.controller;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddArticleDto;
import com.lqs.domain.dto.ArticleListDto;
import com.lqs.domain.vo.PageVo;
import com.lqs.service.ArticleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }


    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){
        return articleService.systemArticleList(pageNum,pageSize,articleListDto);
    }

    /**
     * 后台获取文章
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable Long id){
        return articleService.getArticle(id);
    }


    /**
     * 后台更新文章
     * @param addArticleDto
     * @return
     */
    @PutMapping
    public ResponseResult updateArticle(@RequestBody AddArticleDto addArticleDto){
        return articleService.updateArticle(addArticleDto);
    }


    /**
     * 后台删除文章
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        return articleService.deleteArticle(id);
    }


}
