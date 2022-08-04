package com.lishizhan.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lishizhan.common.constants.SystemConstants;
import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.domain.entity.Article;
import com.lishizhan.common.domain.entity.Category;
import com.lishizhan.common.domain.vo.ArticleDetailVo;
import com.lishizhan.common.domain.vo.HotArticleVo;
import com.lishizhan.common.domain.vo.PageVo;
import com.lishizhan.common.domain.vo.ArticleListVo;
import com.lishizhan.common.service.ArticleService;
import com.lishizhan.common.mapper.ArticleMapper;
import com.lishizhan.common.service.CategoryService;
import com.lishizhan.common.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Admin
 * @description 针对表【ali_article】的数据库操作Service实现
 * @createDate 2022-08-02 15:59:17
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryService categoryService;


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
        log.info("page分页数据：{}",page);
        log.info("条数：{}",page.getTotal());


        //封装查询结果
        List<Article> articleList = page.getRecords();

        //查询categoryName，该查询有的方式有3种
        //1，普通for
        /*for (Article article : articleList) {
            //获取分类id
            Long id = article.getCategoryId();
            //根据分类ID获取分类信息
            Category category = categoryService.getById(id);
            //设置分类名称
            article.setCategoryName(category.getName());
        }*/
        //2，使用stream流的方式
        /*articleList=articleList.stream().map(new Function<Article, Article>() {
            @Override
            public Article apply(Article article) {
                //获取分类id
                Long id = article.getCategoryId();
                //根据分类ID获取分类信息
                Category category = categoryService.getById(id);
                //设置分类名称
                article.setCategoryName(category.getName());
                return article;
            }
        }).collect(Collectors.toList());*/
        //3，使用lambda，但是注意：setCategoryName时是需要返回该对象的，所以需要使用lombok的注解Accessors(chain=true)
        articleList = articleList.stream().map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName())).collect(Collectors.toList());

        //属性拷贝
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);

        //封装前端需要的VO
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 根据文章id查询文章详情
     * @param id 文章ID
     * @return
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据文章id查询文章详情
        Article article = this.getById(id);
        //根据分类id查询分类名称
        Long categoryId = article.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (!ObjectUtils.isEmpty(category))
            article.setCategoryName(category.getName());

        //转为VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //封装响应
        return ResponseResult.okResult(articleDetailVo);
    }

}




