package com.lqs.controller;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.TagListDto;
import com.lqs.domain.entity.Category;
import com.lqs.domain.entity.Tag;
import com.lqs.domain.vo.PageVo;
import com.lqs.domain.vo.TagVo;
import com.lqs.service.TagService;
import com.lqs.utils.BeanCopyUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
@Api(tags = "标签接口")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    @ApiOperation(value = "获取标签", notes = "获取标签列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小"),
            @ApiImplicitParam(name = "tageListDto", value = "存有 名称和备注")
    })
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.tagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping
    @ApiOperation(value = "添加标签")
    public ResponseResult addTag(@RequestBody TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        return tagService.saveTag(tag);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除标签")
    public ResponseResult deleteTag(@PathVariable Long id) {
        return ResponseResult.okResult(tagService.removeById(id));
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个标签")
    public ResponseResult getTag(@PathVariable Long id) {
        return tagService.getTag(id);
    }


    @PutMapping
    @ApiOperation(value = "更新标签数据")
    public ResponseResult updateTag(@RequestBody Tag tag) {
        return tagService.updateTag(tag);
    }


    @GetMapping("/listAllTag")
    @ApiOperation(value = "获取全部标签列表")
    public ResponseResult listAllTag(){
        List<TagVo> tags = tagService.listAllTag();
        return ResponseResult.okResult(tags);
    }

}
