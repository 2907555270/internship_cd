package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.Process;
import com.txy.graduate.mapper.ProcessMapper;
import com.txy.graduate.service.ProcessService;
import org.springframework.stereotype.Service;

@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService  {
}
