package com.txy.graduate.controller;

import com.txy.graduate.config.Result;
import com.txy.graduate.domain.po.Process;
import com.txy.graduate.service.ProcessService;
import com.txy.graduate.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 导出数据控制层
 */
@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private ProcessService processService;

    @GetMapping("/process")
    public Result exportProcess(){
        List<Process> processes = processService.queryAll();
        return Result.result(true,null,1==1?"导出成功 ^_^":"导出失败 -_-");
    }
}
