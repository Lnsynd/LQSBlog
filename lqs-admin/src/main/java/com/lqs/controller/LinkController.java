package com.lqs.controller;

import com.lqs.domain.ResponseResult;
import com.lqs.domain.dto.AddLinkDto;
import com.lqs.domain.entity.Category;
import com.lqs.domain.entity.Link;
import com.lqs.domain.vo.CategoryVo;
import com.lqs.domain.vo.LinkVo;
import com.lqs.domain.vo.PageVo;
import com.lqs.service.LinkService;
import com.lqs.utils.BeanCopyUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Savepoint;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 后台分页获取友联列表
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @param name     友链名
     * @param status   状态
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<PageVo> getPageLinkList(Integer pageNum, Integer pageSize, String name, String status) {
        return ResponseResult.okResult(linkService.getPageLinkList(pageNum, pageSize, name, status));
    }


    /**
     * 添加友联
     * @param addLinkDto
     * @return
     */
    @PostMapping
    public ResponseResult<T> addLink(@RequestBody AddLinkDto addLinkDto){
        linkService.save(BeanCopyUtils.copyBean(addLinkDto, Link.class));
        return ResponseResult.okResult();
    }


    /**
     * 后台修改友联信息
     * (1)友联信息回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable Long id){
        Link link = linkService.getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(link, LinkVo.class));
    }


    /**
     * 后台修改友联信息
     * (2)修改友联信息
     * @param linkVo
     * @return
     */
    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkVo linkVo) {
        linkService.updateById(BeanCopyUtils.copyBean(linkVo, Link.class));
        return ResponseResult.okResult();
    }


    /**
     * 删除友联信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id) {
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
