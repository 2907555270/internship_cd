package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.po.Process;
import com.txy.graduate.mapper.ProcessMapper;
import com.txy.graduate.service.ProcessService;
import com.txy.graduate.util.FileUtil;
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

    @Autowired
    private FileUtil fileUtil;

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
        //为数据添加图片存放的根路径
        String baseImgPath = fileUtil.getRootPath(Process.class);
        if (baseImgPath == null || baseImgPath.equals("")) {
            return false;
        }
        //设置图片存放的根目录
        process.setBaseImgPath(baseImgPath);
        //执行添加操作
        return mapper.insert(joinData(process).get(0)) > 0;
    }

    @Override
    public boolean updateProcessById(Process process) {
        //为process添加BaseImgPath
        process.setBaseImgPath(fileUtil.getRootPath(Process.class));

        //查询新的process中的imgPaths
        List<String> imgPaths = process.getImgPaths();
        //查询旧的process中的imgPaths
        List<String> imgPathsOld = queryById(process.getId()).getImgPaths();

        //删除的旧的图片
        imgPathsOld.forEach(i->{
            if (!imgPaths.contains(i))
                fileUtil.removeFiles(i);
        });

        //执行更新数据库数据
        return mapper.updateById(process) > 0;
    }

    @Override
    public boolean removeProcessById(Long id) {
        //查询对应的process，获取到对应的图片路径信息
        Process process = queryById(id);
        //删除这些图片
        fileUtil.removeFiles(process.getImgPaths().toArray(String[]::new));
        //删除数据库中的数据
        return mapper.deleteById(id) > 0;
    }


    //对数据库中的数据修复成完整的数据
    public List<Process> fixData(Process... processes) {
        if (processes != null && processes.length > 0) {
            return Arrays.stream(processes).peek(process -> {
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
        return Arrays.stream(processes).map(p -> {
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
