package com.lqs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.domain.ResponseResult;
import com.lqs.domain.entity.Link;
import com.lqs.domain.vo.PageVo;

import java.util.List;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-02-22 17:18:15
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    PageVo getPageLinkList(Integer pageNum, Integer pageSize, String name, String status);
}

