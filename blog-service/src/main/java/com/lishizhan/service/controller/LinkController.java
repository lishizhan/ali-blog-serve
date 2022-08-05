package com.lishizhan.service.controller;

import com.lishizhan.common.domain.ResponseResult;
import com.lishizhan.common.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lishizhan
 * @version 1.0
 * @description: TODO
 * @date 2022/8/4 11:39
 */

@RestController
@RequestMapping("link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 获取所有的审核通过的友情链接
     * @return
     */
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }

    @GetMapping("test")
    public ResponseResult test(){
        return ResponseResult.okResult("test接口访问成功！！！");
    }



}
