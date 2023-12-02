package com.zfl19.system.controller;

import com.zfl19.basic.dto.AjaxResult;
import com.zfl19.basic.query.BaseQuery;
import com.zfl19.system.domain.Department;
import com.zfl19.system.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 19zfl
 * @description: 部门控制层
 * @date 2023-12-02
 * @note ResponseBody注解作用；将java对象转换成json格式数据
 *       RequestBody注解作用；接受前端post、delete请求，也适用于get请求
 */
@RestController // RestController = ResponseBody + Controller
@RequestMapping("/dm") // 前端访问的一级路径
public class DepartmentController {

    /**
     * 导入业务层接口
     */
    @Autowired
    private IDepartmentService dmService;

    /**
     * 获取所有部门信息【非分页】
     * @return
     */
    @GetMapping("/all")
    public AjaxResult getDepartmentAllInfo() {
        return AjaxResult.getSuccess(dmService.getDepartmentAllInfo());
    }

    /**
     * 分页查询
     * @param query 分页查询所需要的参数
     * @return 分页查询数据
     */
    @PostMapping("/page")
    public AjaxResult getDepartmentAllInfoByPageList(@RequestBody BaseQuery query) {
        return AjaxResult.getSuccess(dmService.getDepartmentAllInfoByPageList(query));
    }

    /**
     * 根据主键id删除department信息
     * @param id 前端传递的id
     * @return  执行反馈信息
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteDepartmentInfoById(@PathVariable("id") Long id) {
        try {
            dmService.deleteById(id);
            return AjaxResult.getSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getError("删除失败！");
        }
    }

    /**
     * 新增 + 修改
     * @param department 前端传递的department对象数据
     * @return 执行反馈信息
     */
    @PostMapping("/input")
    public AjaxResult addAndUpdateDeparmentInfo(@RequestBody Department department) {
        try {
            dmService.addAndUpdate(department);
            return AjaxResult.getSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getError("操作失败！");
        }
    }

}
