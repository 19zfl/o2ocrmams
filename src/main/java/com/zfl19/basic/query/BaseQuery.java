package com.zfl19.basic.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 19zfl
 * @description: 前端分页数据参数query
 * @date 2023-12-02
 */
@Data
@ApiModel(value = "分页参数", description = "分页参数实体类")
public class BaseQuery {

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    private Integer pageNum;

    /**
     * 每页条数
     */
    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

}
