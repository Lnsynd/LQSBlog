package com.lqs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.TagListDto;
import com.lqs.domain.entity.Category;
import com.lqs.domain.entity.Tag;
import com.lqs.domain.vo.PageVo;
import com.lqs.domain.vo.TagVo;
import com.lqs.service.TagService;
import com.lqs.mapper.TagMapper;
import com.lqs.utils.BeanCopyUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-02 14:44:38
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    /**
     * 获取标签列表
     *
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    @Override
    public ResponseResult<PageVo> tagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        // 分页查询
        LambdaQueryWrapper<Tag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        lambdaQueryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page,lambdaQueryWrapper);

        // 封装数据
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult saveTag(Tag tag) {
        save(tag);
        return ResponseResult.okResult();
    }



    @Override
    public ResponseResult getTag(Long id) {
        Tag tag = getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(Tag tag) {
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return tagVos;
    }


}

