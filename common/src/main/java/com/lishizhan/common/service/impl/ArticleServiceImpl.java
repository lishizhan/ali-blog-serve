package com.lishizhan.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lishizhan.common.constants.SystemConstants;
import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.domain.entity.Article;
import com.lishizhan.common.domain.vo.HotArticleVo;
import com.lishizhan.common.domain.vo.PageVo;
import com.lishizhan.common.domain.vo.ArticleListVo;
import com.lishizhan.common.service.ArticleService;
import com.lishizhan.common.mapper.ArticleMapper;
import com.lishizhan.common.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Admin
 * @description 针对表【ali_article】的数据库操作Service实现
 * @createDate 2022-08-02 15:59:17
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;


    @Override
    public ResponseResult articleListHot() {
        //查询热门文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //构造条件
        //1，必须是正式发表的文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //2，按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //3，最多查10条
        Page<Article> page = new Page<>(SystemConstants.HOT_ARTICLE_CURRENT, SystemConstants.HOT_ARTICLE_SIZE);
        page(page, queryWrapper);
        //获取查询列表
        List<Article> articleList = page.getRecords();
        //封装VO
        //List<HotArticleVo> hotArticleVoList = new ArrayList<>();
        //bean属性拷贝
        /*articleList.forEach(article -> {
            HotArticleVo hotArticleVo = new HotArticleVo();
            BeanUtils.copyProperties(article, hotArticleVo);
            hotArticleVoList.add(hotArticleVo);
        });*/
        List<HotArticleVo> hotArticleVoList = BeanCopyUtils.copyBeanList(articleList, HotArticleVo.class);


        return ResponseResult.okResult(hotArticleVoList);
    }

    /**
     * 需求：
     * 在首页和分类页面都需要查询文章列表
     * 首页：查询所用文章
     * 分类页面：查询对应分类的文章
     * 要求：只能查询正式发布的文章，置顶的文章要显示在前面
     *
     * @param pageNum    页码
     * @param pageSize   每页展示条数
     * @param categoryId 分类id
     * @return
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //如果categoryId,查询时候要传入
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);

        //状态是是正式发布的，对isTop进行降序
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);

        //封装查询结果
        List<Article> articleList = page.getRecords();
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);

        //封装前端需要的VO
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

}




