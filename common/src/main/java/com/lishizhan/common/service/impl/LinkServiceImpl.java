package com.lishizhan.common.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lishizhan.common.constants.SystemConstants;
import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.domain.entity.Link;
import com.lishizhan.common.domain.vo.LinkVo;
import com.lishizhan.common.service.LinkService;
import com.lishizhan.common.mapper.LinkMapper;
import com.lishizhan.common.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Admin
* @description 针对表【ali_link(友链)】的数据库操作Service实现
* @createDate 2022-08-04 11:31:22
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Autowired
    private LinkService linkService;

    /**
     * 查询所有审核通过的友情链接
     * @return
     */
    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        //审核通过
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换VO
        List<LinkVo> linkVoList = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVoList);
    }
}




