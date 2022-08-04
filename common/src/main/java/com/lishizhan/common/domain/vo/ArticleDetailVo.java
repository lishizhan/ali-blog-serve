package com.lishizhan.common.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lishizhan
 * @version 1.0
 * @description: TODO
 * @date 2022/8/4 11:03
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {

    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;
    //所属分类名
    private String categoryName;
    //缩略图
    private String thumbnail;

    //文章内容
    private String content;
    //访问量
    private Long viewCount;

    private Date createTime;

}

