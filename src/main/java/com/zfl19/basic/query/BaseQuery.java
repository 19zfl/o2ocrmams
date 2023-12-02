package com.zfl19.basic.query;

import lombok.Data;

/**
 * @author: 19zfl
 * @description: 前端分页数据参数query
 * @date 2023-12-02
 */
@Data
public class BaseQuery {

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

}
