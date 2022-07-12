package com.txy.graduate.controller;

import com.txy.graduate.config.Result;
import com.txy.graduate.domain.Process;
import com.txy.graduate.service.ProcessService;
import com.txy.graduate.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private FileUtil fileUtil;


    //查询所有的流程配置信息
    @GetMapping()
    public Result findAll() {
        List<Process> list = processService.queryAll();
        boolean flag = list.size() > 0;
        return Result.result(flag, list, flag ? null : "未查询到任何数据 -_-");
    }

    //根据id查询某个流程的详细信息
    @GetMapping("{id}")
    public Result findById(@PathVariable Long id) {
        Process process = processService.queryById(id);
        boolean flag = process != null;
        return Result.result(flag, process, flag ? null : "未获取到数据 -_-");
    }

    //上传流程对应的地点图片
    @PostMapping("upload")
    public Result upload(@RequestParam("pics") MultipartFile[] multipartFiles) {
        try {
            return fileUtil.uploadPics(multipartFiles, Process.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.result(false,null,"图片上传失败 -_-");
        }
    }

    //添加新的流程信息
    @PutMapping()
    public Result save(@RequestBody @Validated Process process, BindingResult result) {
        //效验配置信息数据是否有错
        List<FieldError> fieldErrors = result.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            return Result.result(false, null, fieldErrors.get(0).getDefaultMessage());
        }

        //添加数据到数据库
        boolean flag = processService.saveProcess(process);
        return Result.result(flag, null, flag ? "添加成功 ^_^" : "添加失败 -_-");
    }

    //按process_id更新流程信息：可以更新保存的图片信息
    @PostMapping()
    public Result update(@RequestBody Process process) {
        //修改流程配置对应的图片+修改流程配置信息===放在service中进行操作
        boolean flag = processService.updateProcessById(process);
        return Result.result(flag, null, flag ? "修改成功 ^_^" : "修改失败 -_-");
    }

    //按process_id删除流程信息：同时删除保存的图片信息
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable Long id) {
        //删除该流程配置信息 == 放在service中进行，对上层透明
        boolean flag = processService.removeProcessById(id);
        return Result.result(flag, null, flag ? "删除成功 ^_^" : "删除失败 -_-");
    }

}

