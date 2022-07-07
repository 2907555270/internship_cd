package com.txy.graduate.controller;

import com.txy.graduate.config.Result;
import com.txy.graduate.domain.Process;
import com.txy.graduate.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    //查询所有离校流程信息
    @GetMapping()
    public Result findAll() {
        List<Process> list = processService.list();
        return new Result(list.size() > 0, list, list.size() > 0 ? null : "未获取到任何数据 -_-");
    }

    //根据id查询某一阶段的离校流程信息
    @GetMapping("{id}")
    public Result findByTitle(@PathVariable int id) {
        Process process = processService.getById(id);
        return new Result(process != null, process, process != null ? null : "未获取到数据 -_-");
    }

    //根据id更新某一阶段的离校流程
    @PostMapping()
    public Result updateById(@RequestBody Process process) {
        Boolean flag = processService.updateById(process);
        return new Result(flag,null,flag?"修改成功 ^_^":"修改失败 -_-" );
    }

    //添加新的离校流程信息
    @PutMapping()
    public Result save(@RequestBody Process process){
        Boolean flag = processService.save(process);
        return new Result(flag,null,flag?"添加成功 ^_^":"添加失败 -_-");
    }

    //根据Id删除的离校流程信息
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable int id){
        Boolean flag = processService.removeById(id);
        return new Result(flag,null,flag?"删除成功 ^_^":"删除失败 -_-");
    }

}
