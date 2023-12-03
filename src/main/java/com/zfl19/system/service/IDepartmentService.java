package com.zfl19.system.service;

import com.zfl19.basic.query.BaseQuery;
import com.zfl19.basic.query.PageList;
import com.zfl19.system.domain.Department;

import java.util.List;

/**
 * @author: 19zfl
 * @description: 部门业务层
 * @date 2023-12-02
 */
public interface IDepartmentService {

    /**
     * 获取部门所有信息
     * @return
     */
    List<Department> getDepartmentAllInfo();

    /**
     * 通过分页工具获取所有部门信息
     * @return
     */
    PageList<Department> getDepartmentAllInfoByPage(BaseQuery query);

    /**
     * 根据主键id删除部门信息
     * @param id
     */
    void deleteById(Long id);

    /**
     * 新增 + 修改
     * @param department
     */
    void addAndUpdate(Department department);

}
