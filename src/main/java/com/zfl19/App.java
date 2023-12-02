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
