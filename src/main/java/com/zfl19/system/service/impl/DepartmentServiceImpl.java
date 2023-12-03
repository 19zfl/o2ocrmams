package com.zfl19.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zfl19.basic.query.BaseQuery;
import com.zfl19.basic.query.PageList;
import com.zfl19.system.domain.Department;
import com.zfl19.system.mapper.DepartmentMapper;
import com.zfl19.system.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 19zfl
 * @description: 部门业务层实现类
 * @date 2023-12-02
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    /**
     * 导入Mapper持久层
     */
    @Autowired
    private DepartmentMapper dmMapper;

    /**
     * 获取所有部门信息
     * @return 所有数据
     */
    @Override
    public List<Department> getDepartmentAllInfo() {
        return dmMapper.selectAll();
    }

    /**
     * 通过分页工具获取所有部门信息
     * @param query 分页参数
     * @return  分页数据
     */
    @Override
    public PageList<Department> getDepartmentAllInfoByPage(BaseQuery query) {
        // 分页查询三步骤：
        // 1. 设置分页参数
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        // 2，执行查询操作
        List<Department> departments = dmMapper.getDepartmentAllInfoByPage();
        // 3. 封装分页数据
        PageInfo<Department> departmentPageInfo = new PageInfo<>(departments);
        // 4. 使用PageList进行数据传递
        PageList<Department> departmentPageList = new PageList<>(departmentPageInfo.getTotal(), departmentPageInfo.getList());
        // 5. 返回数据
        return departmentPageList;
    }

    /**
     * 根据主键id删除部门信息
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        dmMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增 + 修改
     * @param department
     */
    @Override
    public void addAndUpdate(Department department) {
        // 1. 判断是要执行新增还是修改操作=判断department中id的值是否为空
        if (department.getId() == null) {
            // 如果为空：新增
            dmMapper.insertSelective(department);
        } else {
            // 如果不为空：修改
            dmMapper.updateByPrimaryKeySelective(department);
        }
    }

}