package com.lishizhan.common.constants;

/**
 * @Author : Alishiz
 * @Date : 2022/8/3/0003
 * @email : 1575234570@qq.com
 * @Description : 字符字面量
 */
public class SystemConstants {
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 分类是正常状态
     */
    public static final String  STATUS_NORMAL = "0";
    /**
     * 友链状态为审核通过
     */
    public static final String  LINK_STATUS_NORMAL = "0";
    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * 热门文章的分页数
     * current – 当前页 size – 每页显示条数
     * */
    public static final int HOT_ARTICLE_CURRENT = 1;
    public static final int HOT_ARTICLE_SIZE = 10;


    /**
     * 登陆请求头
     * */
    public static final String JWT_TOKEN = "token";



}
