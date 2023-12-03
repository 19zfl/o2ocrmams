package com.zfl19.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author: 19zfl
 * @description: 部门实体类
 * @date 2023-12-02
 */
@Data
@AllArgsConstructor // 有参构造器
@NoArgsConstructor  // 无参构造器
@Table(name = "t_department")   // 当前操作的表
public class Department {

    /**
     * 部门id
     */
    @Id
    private Long id;
    /**
     * 部门编号
     */
    private String sn;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门状态
     */
    private Integer state = 1;
    /**
     * 部门经理id
     */
    private Long managerId;
    /**
     * 上级部门id
     */
    private Long parentId;
    /**
     * 部门路径
     */
    private String dirPath;

    /**
     * 部门经理名称
     */
    @Transient
    private String managerName;

    /**
     * 上级部门名称
     */
    @Transient
    private String parentName;

}
