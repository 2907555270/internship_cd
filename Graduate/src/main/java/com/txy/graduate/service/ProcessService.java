package com.txy.graduate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.domain.Process;

import java.util.List;

public interface ProcessService extends IService<Process> {
    //查询所有数据
    List<Process> queryAll();

    //按id查询某个数据
    Process queryById(Long id);

    //插入一个新的process：主要是图片处理
    boolean saveProcess(Process process);

    //更新process: 主要是图片处理
    boolean updateProcessById(Process process);

    //删除process: 主要是图片处理
    boolean removeProcessById(Long id);
}
