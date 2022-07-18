package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.po.Process;
import com.txy.graduate.mapper.ProcessMapper;
import com.txy.graduate.service.ProcessService;
import com.txy.graduate.util.FileUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService {

    @Resource(name = "processMapper")
    private ProcessMapper mapper;

    @Resource(name = "fileUtil")
    private FileUtil fileUtil;

    private static final String[] baseColumns = {"id", "title", "content", "address", "start_time", "end_time", "phone",
            "type", "note", "img", "base_img_path"};

    @Override
    public List<Process> queryAll() {
        QueryWrapper<Process> wrapper = new QueryWrapper<>();
        wrapper.select(baseColumns).eq("status", 1);
        List<Process> processes = mapper.selectList(wrapper);
        //对查询到的结果进行处理
        return fixData(processes.toArray(Process[]::new));
    }

    @Override
    public Process queryById(Long id) {
        Process process = mapper.selectById(id);
        if (process == null)
            return null;
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
        Process query = queryById(process.getId());
        if (query == null)
            return false;
        List<String> imgPathsOld = query.getImgPaths();

        //删除的旧的图片
        imgPathsOld.forEach(i -> {
            if (imgPaths == null || !imgPaths.contains(i))
                fileUtil.removeFiles(i);
        });

        //执行更新数据库数据
        return mapper.updateById(process) > 0;
    }

    @Override
    public boolean removeProcessById(Long id) {
        //查询对应的process，获取到对应的图片路径信息
        Process process = queryById(id);
        if (process == null)
            return false;
        //删除这些图片
        fileUtil.removeFiles(process.getImgPaths().toArray(String[]::new));
        //删除数据库中的数据
        return mapper.deleteById(id) > 0;
    }


    //对数据库中的数据修复成完整的数据
    public List<Process> fixData(Process... processes) {
        if (processes[0] != null) {
            List<Process> result = new ArrayList<>();
            for (Process process : processes) {//获取存放的根路径
                String baseImgPath = process.getBaseImgPath();
                //补全图片的完整路径
                List<String> imgPaths = process.getImgPaths();
                if (imgPaths == null)
                    imgPaths = new ArrayList<>();
                if (process.getImg() != null) {
                    List<String> finalImgPaths = imgPaths;
                    List<String> list = new ArrayList<>();
                    Collections.addAll(list, process.getImg().split(";"));
                    list.forEach(s -> finalImgPaths.add(baseImgPath + "/" + s));
                }
                process.setImgPaths(imgPaths);
                //将无效的数据置null
                process.setImg(null);
                process.setBaseImgPath(null);
                result.add(process);
            }
            return result;
        }
        return null;
    }

    //对前端发来的数据进行拼接，img[] --> img
    public List<Process> joinData(Process... processes) {
        List<Process> list = new ArrayList<>();
        for (Process p : processes) {//获取存放的根路径
            String baseImgPath = p.getBaseImgPath();
            List<String> imgPaths = p.getImgPaths();
            //裁剪、拼接img
            if (imgPaths != null && !imgPaths.isEmpty()) {
                String img = imgPaths.stream().map(s -> s.replaceFirst(baseImgPath, "")).collect(Collectors.joining(";"));
                p.setImg(img);
                list.add(p);
            }
        }
        return list;
    }
}
