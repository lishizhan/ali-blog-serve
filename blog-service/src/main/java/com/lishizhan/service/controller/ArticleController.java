package com.lishizhan.service.controller;

import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lishizhan
 * @version 1.0
 * @description: TODO
 * @date 2022/8/2 16:44
 */
@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询热门文章
     * @return
     */
    @GetMapping("/hotArticleList")
    public ResponseResult ArticleListHot(){
        return articleService.articleListHot();
    }

    /**
     * 分页查询文章列表
     */
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    /**
     * 根据文章id查询文章详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult article(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }




}
