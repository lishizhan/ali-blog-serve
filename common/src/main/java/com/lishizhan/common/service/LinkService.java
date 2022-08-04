package com.lishizhan.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.domain.entity.Link;

/**
* @author Admin
* @description 针对表【ali_link(友链)】的数据库操作Service
* @createDate 2022-08-04 11:31:22
*/
public interface LinkService extends IService<Link> {

    /**
     * 获取已经审核通过的友情链接
     * @return
     */
    ResponseResult getAllLink();

}
