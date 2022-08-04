package com.lishizhan.common.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author : lishizhan
 * @Date : 2022/8/3/0003
 * @email : 1575234570@qq.com
 * @Description : 分页VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo {

    private List rows;
    private Long total;
}
