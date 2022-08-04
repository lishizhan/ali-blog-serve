package com.lishizhan.common.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName ali_article
 */
@Data
@Accessors(chain = true)  //该配置是setXXX时返回该对象
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ali_article")
public class Article implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     *
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章类型：1文章、2草稿
     */
    private String type;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 所属分类ID
     */
    private Long categoryId;
    /*分类名称*/
    @TableField(exist = false)
    private String categoryName;

    /**
     * 缩略图
     */
    private String thumbnall;

    /**
     * 是否顶置
     */
    private String isTop;

    /**
     * 状态0已发布，1草稿
     */
    private String status;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 访问量
     */
    private Long viewCount;

    /**
     * 
     */
    private String isComment;

    /**
     * 
     */
    private Long createBy;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Long updateBy;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 0表示未删除
     */
    private Integer delFlag;

    private static final long serialVersionUID = 1L;
}