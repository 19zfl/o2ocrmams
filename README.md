# O2O CRMAMS系统开发步骤

### 一、项目基础搭建

##### 1. 建库建表

> sql文件存放再项目目录的db文件夹中

##### 2. 创建一个maven项目

> O2OCRMAMS

##### 3. 导入项目需要的依赖

```pom.xml
    <!-- SpringBoot项目的标志 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.5.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <!-- springboot整合mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.1.1</version>
        </dependency>
        <!-- pagehelper分页插件 -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.10</version>
        </dependency>
        <!-- tkmybatis-->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>2.1.5</version>
        </dependency>
        <!-- mysql驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.32</version>
        </dependency>
        <!-- fastjson 处理json数据 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.47</version>
        </dependency>
        <!-- 字符串处理工具 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.2</version>
        </dependency>
        <!-- 小辣椒lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.8</version>
            <scope>provided</scope>
        </dependency>
        <!-- hutool -->
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.7.22</version>
        </dependency>
    </dependencies>
```

##### 4. 创建启动类

```App.java
package com.zfl19;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: 19zfl
 * @description: 项目启动类
 * @date 2023-12-02
 */

@SpringBootApplication
@MapperScan("com.zfl19.*.mapper")   // 使用通配符，让启动类能够扫描到每个包中的mapper
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```

##### 5. 项目建包

![](https://gitee.com/coder_zfl/markdown-image-cloud-drive/raw/master/markdown/202312021434826.png)

解释：

- basic存放异常捕获，跨域等代码
- system存放项目核心业务实现的代码

### 二、部门业务实现

##### 1. 项目配置文件

1.项目端口号

2.数据库连接信息

3.日志信息打印

```application.yml
# 端口号
server:
  port: 80
# 配置数据库连接信息
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql:///o2ocrmams
    username: root
    password: 123456
# 日志信息打印
logging:
  level:
    com:
      zfl19: debug
```

##### 2. 部门实体类

```Department.java
package com.zfl19.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

/**
 * @author: 19zfl
 * @description: 部门实体类
 * @date 2023-12-02
 */
@Data
@AllArgsConstructor // 有参构造器
@NoArgsConstructor  // 无参构造器
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
    private Integer state;
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

}
```

##### 3. 部门持久层

```DepartmentMapper.java
package com.zfl19.system.mapper;

import com.zfl19.system.domain.Department;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author: 19zfl
 * @description: 部门持久层
 * @date 2023-12-02
 */
public interface DepartmentMapper extends Mapper<Department> {
	// 基础的CRUD通过继承tk的Mapper来实现
}
```

##### 4. 接口测试

点击DepartmentMapper按住alt + enter快速创建单元测试类

 ```DepartmentMapperTest.java
 package com.zfl19.system.mapper;
 
 import com.zfl19.App;
 import com.zfl19.system.domain.Department;
 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.test.context.junit4.SpringRunner;
 
 import java.util.List;
 
 import static org.junit.Assert.*;
 
 @RunWith(SpringRunner.class)
 @SpringBootTest(classes = App.class)
 public class DepartmentMapperTest {
 
     @Autowired
     private DepartmentMapper mapper;
 
     @Test
     public void test01() {
         List<Department> departments = mapper.selectAll();
         departments.forEach(System.err::println);
     }
 
 }
 ```

运行得到下图所示数据就说明接口没有问题：

![](https://gitee.com/coder_zfl/markdown-image-cloud-drive/raw/master/markdown/202312021545928.png)

##### 5. 编写分页插件

```PageList.java
package com.zfl19.basic.query;

import lombok.Data;

import java.util.List;

/**
 * @author: 19zfl
 * @description: 分页工具：操作数据分页
 * @date 2023-12-02
 */
@Data
public class PageList<T> {

    private Long total;

    private List<T> list;

}
```

##### 6. 编写分页参数接受类

```BaseQuery.java
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
```



##### 7. 编写业务层接口

```IDepartmentService.java
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
    PageList<Department> getDepartmentAllInfoByPageList(BaseQuery query);

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
```

##### 8. 业务层接口实现

```DepartmentServiceImpl.java
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
    public PageList<Department> getDepartmentAllInfoByPageList(BaseQuery query) {
        // 分页查询三步骤：
        // 1. 设置分页参数
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        // 2，执行查询操作
        List<Department> departments = dmMapper.selectAll();
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
```

##### 9. 返回前端数据的封装

```AjaxResult.java
package com.zfl19.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 19zfl
 * @description: 将所有返回给前端的数据封装成统一的格式
 * @date 2023-12-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxResult {

    /**
     * code：状态码，便于开发者定位问题
     */
    private String code;

    /**
     * success：请求状态
     */
    private Boolean success;

    /**
     * msg：返回的信息
     */
    private String msg;

    /**
     * data：返回给前端的数据
     */
    private Object data;

    /**
     * 没有数据传送的请求成功
     * @return
     */
    public static AjaxResult getSuccess() {
        return new AjaxResult("200", true, "请求成功！", null);
    }

    /**
     * 有数据需要返回给前端的请求成功
     * @param obj
     * @return
     */
    public static AjaxResult getSuccess(Object obj) {
        return new AjaxResult("210", true, "请求成功！", obj);
    }

    /**
     * 请求失败
     * @param msg
     * @return
     */
    public static AjaxResult getError(String msg) {
        return new AjaxResult("202", false, msg, null);
    }

    /**
     * 请求失败
     * @param code  自定义code
     * @param msg   自定义msg
     * @return
     */
    public static AjaxResult getError(String code, String msg) {
        return new AjaxResult(code, false, msg, null);
    }

}
```



##### 10. 部门控制层

```DepartmentController.java
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
```

##### 11. 接口测试

- Run App
- 打开postman，如下图：

查询所有：

![](https://gitee.com/coder_zfl/markdown-image-cloud-drive/raw/master/markdown/202312022231035.png)

分页查询：

![](https://gitee.com/coder_zfl/markdown-image-cloud-drive/raw/master/markdown/202312022243245.png)

### 三、集成接口文档SwaggerUI

##### 1. 导入SwaggerUI依赖

```pom.xml
		<dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```

##### 2. 新增SwaggerUI配置文件

```SwaggerConfig.java
package com.zfl19.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: 19zfl
 * @description: SwaggerUI配置类
 * @date 2023-12-02
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zfl19")) // project package scan
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统接口文档")
                .description("部门模块")
                .contact(new Contact("19zfl","19zfl.github.io", "19aleiya@gmail.com"))
                .version("1.0.0")
                .build();
    }

}
```

##### 3. SwaggerUI提供的注解

- @Api：放置在controller类名上；
- @ApiOperation：放置在controller类中方法名上；
- @ApiParam：放置在controller类方法的参数前；
- @ApiModel：放置在实体类类名上；
- @ApiModelProperty：放置在实体类中属性字段上。

### 四、非关系型数据库Redis整合

##### 1. 导入依赖

```pom.xml
<dependency>
	<groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

##### 2. 配置yml文件

```application.yml
# 端口号
server:
  port: 80
# 配置数据库连接信息
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql:///o2ocrmams
    username: root
    password: 123456
  # redis连接信息
  redis:
    host: 127.0.0.1
    port: 6379
    timeout: 1000
    password:
    jedis:
      pool:
        max-active: 30  # 给定时间可以分配的最大连接数，使用负值表示没有限制
        max-idle: 10  # 最大空闲连接数
        min-idle: 1   # 最小空闲连接数
        max-wait: -1  # 连接池最大等待时间 -1表示没有限制
# 日志信息打印
logging:
  level:
    com:
      zfl19: debug
```

##### 3. 测试

在之前的测试类中注入StringRedisTemplate，如下：

![](https://gitee.com/coder_zfl/markdown-image-cloud-drive/raw/master/markdown/202312031102356.png)

运行完之后redis中存在这个key和value就说明redis整合是没有问题的：

![image-20231203110343725](C:\Users\zfl19\AppData\Roaming\Typora\typora-user-images\image-20231203110343725.png)

### 五、后台管理系统前端搭建

后端项目端口号配置，如果不是8080就需要将端口号改成对应后端端口号：

![](https://gitee.com/coder_zfl/markdown-image-cloud-drive/raw/master/markdown/202312031309620.png)

配置号前端请求接口后是没有办法访问后端，需要在后端配置跨域或者前端配置；

![](https://gitee.com/coder_zfl/markdown-image-cloud-drive/raw/master/markdown/202312031315088.png)

##### 1. 跨域配置类

```GlobalCorsConfig.java\
package com.zfl19.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author: 19zfl
 * @description: 前端跨域访问配置类
 * @date 2023-12-03
 */
@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 1. 添加允许访问的ip
        config.addAllowedOrigin("http://127.0.0.1:6001");
        config.addAllowedOrigin("http://localhost:6001");
        // 2. 配置是否发送cookie信息
        config.setAllowCredentials(true);
        // 3. 配置前端允许请求的方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        // 4. 允许请求头信息
        config.addAllowedHeader("*");
        // 5. 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", config);
        // 6. 返回
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
```

##### 2. 接口连通性测试

![](https://gitee.com/coder_zfl/markdown-image-cloud-drive/raw/master/markdown/202312031456696.png)

表单中的数据信息展示不完整，就需要重写sql语句实现；
