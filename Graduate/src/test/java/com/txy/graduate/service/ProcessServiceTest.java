package com.txy.graduate.service;

import com.txy.graduate.domain.po.Process;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@SpringBootTest
public class ProcessServiceTest {
    @Resource
    private ProcessService processService;

    @Resource
    private DataSource dataSource;

    @Test
    public void testQueryAll(){
        List<Process> processes = processService.queryAll();
        for (Process process:processes ) {
            System.out.println(process);
        }
    }
}
