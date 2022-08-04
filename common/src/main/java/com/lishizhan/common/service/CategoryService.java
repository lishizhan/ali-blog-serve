package com.lishizhan.common.service;

import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface CategoryService extends IService<Category> {

    /**
     * 查询首页分类
     * @return
     */
    ResponseResult getCategoryList();

}
