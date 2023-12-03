package com.zfl19.system.mapper;

import com.zfl19.App;
import com.zfl19.system.domain.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class DepartmentMapperTest {

    @Autowired
    private DepartmentMapper mapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test01() {
        List<Department> departments = mapper.selectAll();
        departments.forEach(System.err::println);
    }

    @Test
    public void test02() {
        redisTemplate.opsForValue().set("name", "刘十三");
    }

}