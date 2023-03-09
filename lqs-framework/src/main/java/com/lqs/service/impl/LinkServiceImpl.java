package com.lqs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.constants.SystemConstants;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.Category;
import com.lqs.domain.entity.Link;
import com.lqs.domain.vo.LinkVo;
import com.lqs.domain.vo.PageVo;
import com.lqs.service.LinkService;
import com.lqs.mapper.LinkMapper;
import com.lqs.utils.BeanCopyUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-02-22 17:18:16
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult<T> getAllLink() {
        // 查询审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> list = list(queryWrapper);

        // 封装成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public PageVo getPageLinkList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(name), Link::getName, name);
        queryWrapper.eq(StringUtils.hasText(status), Link::getStatus, status);

        // 分页查询
        Page<Link> page = new Page<>(pageNum, pageSize);

        page(page, queryWrapper);

        return new PageVo(page.getRecords(), page.getTotal());
    }
}

