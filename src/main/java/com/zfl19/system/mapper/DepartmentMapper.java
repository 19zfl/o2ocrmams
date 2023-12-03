package com.zfl19.system.mapper;

import com.zfl19.system.domain.Department;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: 19zfl
 * @description: 部门持久层
 * @date 2023-12-02
 */
public interface DepartmentMapper extends Mapper<Department> {

    /**
     * 查询所有部门信息
     * @return
     */
    List<Department> getDepartmentAllInfoByPage();

}
