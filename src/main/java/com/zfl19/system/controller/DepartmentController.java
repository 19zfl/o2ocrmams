package com.zfl19.system.controller;

import com.zfl19.basic.dto.AjaxResult;
import com.zfl19.basic.query.BaseQuery;
import com.zfl19.basic.query.PageList;
import com.zfl19.system.domain.Department;
import com.zfl19.system.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "部门模块接口", tags = {"部门模块接口"})
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
    @ApiOperation(value = "获取所有部门信息【非分页】", notes = "获取所有部门信息【非分页】")
    public AjaxResult getDepartmentAllInfo() {
        return AjaxResult.getSuccess(dmService.getDepartmentAllInfo());
    }

    /**
     * 分页查询
     * @param query 分页查询所需要的参数
     * @return 分页查询数据
     */
    @PostMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public AjaxResult getDepartmentAllInfoByPageList(@ApiParam(value = "分页参数") @RequestBody BaseQuery query) {
        return AjaxResult.getSuccess(dmService.getDepartmentAllInfoByPage(query));
    }

    /**
     * 根据主键id删除department信息
     * @param id 前端传递的id
     * @return  执行反馈信息
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除某一部门信息", notes = "删除某一部门信息")
    public AjaxResult deleteDepartmentInfoById(@ApiParam(value = "主键ID") @PathVariable("id") Long id) {
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
    @ApiOperation(value = "新增 + 修改", notes = "根据id是否为空确定是新增或者修改")
    public AjaxResult addAndUpdateDeparmentInfo(@ApiParam(value = "某一部门信息") @RequestBody Department department) {
        try {
            dmService.addAndUpdate(department);
            return AjaxResult.getSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getError("操作失败！");
        }
    }

}
