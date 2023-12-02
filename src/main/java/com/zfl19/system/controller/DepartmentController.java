package com.zfl19.system.controller;

import com.zfl19.basic.dto.AjaxResult;
import com.zfl19.system.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 19zfl
 * @description: 部门控制层
 * @date 2023-12-02
 * @note ResponseBody注解作用；将java对象转换成json格式数据
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
    public AjaxResult getDepartmentAllInfo() {
        return AjaxResult.getSuccess(dmService.getDepartmentAllInfo());
    }

}
