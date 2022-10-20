package com.txy.graduate.controller;

import com.txy.graduate.config.Result;
import com.txy.graduate.domain.po.Process;
import com.txy.graduate.service.ProcessService;
import com.txy.graduate.util.FileUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


//TODO:暂时没有分配流程管理的相关人员，以管理员进行分配
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Resource
    private ProcessService processService;

    @Resource
    private FileUtil fileUtil;


    //查询所有的流程配置信息
    @GetMapping("/list/{schoolCode}")
    public Result findAll(@PathVariable String schoolCode) {
        List<Process> list = processService.queryAll(schoolCode);
        boolean flag = list.size() > 0;
        return Result.result(flag?200:404,flag, flag ? "查询成功 ^_^" : "未查询到任何数据 -_-", list);
    }

    //根据id查询某个流程的详细信息
    @GetMapping("/one/{id}")
    public Result findById(@PathVariable Long id) {
        Process process = processService.queryById(id);
        boolean flag = process != null;
        return Result.result(flag?200:404,flag, flag ? "查询成功 ^_^" : "未查询到数据 -_-", process);
    }

    //上传流程对应的地点图片
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile[] file) {
        try {
            return fileUtil.uploadPics(file, Process.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.result(500,false,"图片上传失败 -_-",null);
        }
    }

    //添加新的流程信息
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/save")
    public Result save(@RequestBody @Validated Process process, BindingResult result) {
        //效验配置信息数据是否有错
        List<FieldError> fieldErrors = result.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            return Result.result(false, null, fieldErrors.get(0).getDefaultMessage());
        }

        //添加数据到数据库
        boolean flag = processService.saveProcess(process);
        return Result.result(flag?200:500,flag,  flag ? "添加成功 ^_^" : "添加失败 -_-",null);
    }

    //按process_id更新流程信息：可以更新保存的图片信息
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/update")
    public Result update(@RequestBody Process process) {
        //修改流程配置对应的图片+修改流程配置信息===放在service中进行操作
        boolean flag = processService.updateProcessById(process);
        return Result.result(flag?200:500,flag,  flag ? "修改成功 ^_^" : "修改失败 -_-",null);
    }

    //按process_id删除流程信息：同时删除保存的图片信息
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Long id) {
        //删除该流程配置信息 == 放在service中进行，对上层透明
        boolean flag = processService.removeProcessById(id);
        return Result.result(flag?200:500, flag, flag ? "删除成功 ^_^" : "删除失败 -_-",null);
    }

}

