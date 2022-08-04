package com.lishizhan.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lishizhan.common.constants.SystemConstants;
import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.domain.entity.Article;
import com.lishizhan.common.domain.entity.Category;
import com.lishizhan.common.domain.vo.CategoryVo;
import com.lishizhan.common.service.ArticleService;
import com.lishizhan.common.service.CategoryService;
import com.lishizhan.common.mapper.CategoryMapper;
import com.lishizhan.common.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Resource
    private ArticleService articleService;

    /**
     * 分步查询分类和文章表状态为已经发布
     * @return
     */
    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //1，查询文章表，状态为已经发布
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleService.list(queryWrapper);

        //2，获取文章的分类ID，并且去重
        Set<Long> categoryIds = articles.stream().map(article -> article.getCategoryId()).collect(Collectors.toSet());

        //3, 查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().filter(category -> SystemConstants.LINK_STATUS_NORMAL.equals(category.getStatus())).collect(Collectors.toList());

        //3，封装VO
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}




