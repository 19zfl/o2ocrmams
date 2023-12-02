package com.zfl19.basic.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: 19zfl
 * @description: 分页工具：操作数据分页
 * @date 2023-12-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageList<T> {

    private Long total;

    private List<T> list;

}
