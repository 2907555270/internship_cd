package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.Process;
import com.txy.graduate.mapper.ProcessMapper;
import com.txy.graduate.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService {

    @Autowired
    private ProcessMapper mapper;

    @Override
    public List<Process> queryAll() {
        List<Process> processes = mapper.selectList(null);
        //对查询到的结果进行处理
        return fixData(processes.toArray(Process[]::new));
    }

    @Override
    public Process queryById(Long id) {
        Process process = mapper.selectById(id);
        //对查询到的结果进行处理
        return fixData(process).get(0);
    }

    @Override
    public boolean saveProcess(Process process) {
        return mapper.insert(joinData(process).get(0)) > 0;
    }

    @Override
    public boolean updateProcessById(Process process) {
        return mapper.updateById(joinData(process).get(0))>0;
    }


    //对数据库中的数据修复成完整的数据
    public List<Process> fixData(Process... processes) {
        if(processes!=null && processes.length>0){
            return Arrays.stream(processes).peek(process->{
                //获取存放的根路径
                String baseImgPath = process.getBaseImgPath();
                //补全图片的完整路径
                List<String> imgPaths = process.getImgPaths();
                if (imgPaths == null)
                    imgPaths = new ArrayList<>();
                if (process.getImg() != null) {
                    List<String> finalImgPaths = imgPaths;
                    Arrays.stream(process.getImg().split(";")).collect(Collectors.toList()).forEach(s -> {
                        finalImgPaths.add(baseImgPath + "/" + s);
                    });
                }
                process.setImgPaths(imgPaths);
            }).collect(Collectors.toList());
        }
        return null;
    }

    //对前端发来的数据进行拼接，img[] --> img
    public List<Process> joinData(Process... processes) {
        return Arrays.stream(processes).map(p->{
            //获取存放的根路径
            String baseImgPath = p.getBaseImgPath();
            List<String> imgPaths = p.getImgPaths();
            //裁剪、拼接img
            String img = imgPaths.stream().map(s -> {
                return s.replaceFirst(baseImgPath, "");
            }).collect(Collectors.joining(";"));

            p.setImg(img);
            return p;
        }).collect(Collectors.toList());
    }
}
