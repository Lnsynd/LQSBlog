package com.lqs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.TagListDto;
import com.lqs.domain.entity.Category;
import com.lqs.domain.entity.Tag;
import com.lqs.domain.vo.PageVo;
import com.lqs.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-03-02 14:44:38
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> tagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult saveTag(Tag tag);


    ResponseResult getTag(Long id);

    ResponseResult updateTag(Tag tag);

    List<TagVo> listAllTag();
}

