package com.txy.graduate.util;

import com.txy.graduate.config.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
public class FileUtil {

    @Value("${img.root.path}")
    private String img_root_path;

    //允许上传的图片格式
    private final String[] imgType = {"jpg", "png", "bmp", "gif"};

    //命名随机值的最大范围：如果经常出现重名，则将数值调大
    private final int range = 1000;

    //获取图片存储的根路径
    public <T> String getRootPath(Class<T> tClass) {
        return img_root_path + tClass.getSimpleName();
    }

    //上传图片：支持的图片格式为：jpg,png,gif,bmp
    public <T> Result uploadPics(MultipartFile[] multipartFiles, Class<T> tClass) throws IOException {

        //获取图片存放的路径名：root_path/tClass_name/
        String path = getRootPath(tClass);

        //初始化存储目录
        File root_dir = new File(path);
        System.out.println(root_dir);
        //若当前目录不存在 或 当前路径并非一个目录
        if (!root_dir.exists() || !root_dir.isDirectory()) {
            System.out.println("文件目录不存在，正在创建目录...");
            //文件目录创建成功
            if (root_dir.mkdir())
                System.out.println("文件目录初始化成功");
            else
                return Result.result(false, null, "文件目录初始化失败:" + root_dir.getName());
        }

        //判断图片资源是否为空
        if (multipartFiles == null || multipartFiles.length < 1) {
            return Result.result(false, null, "图片内容为空，上传失败 -_-");
        }

        //遍历图片资源，并保存，然后返回路径
        String[] newImgPaths = new String[multipartFiles.length];
        int index = 0;
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile != null) {
                //获取文件后缀名
                String fileName = multipartFile.getOriginalFilename();
                String fileType = Objects.requireNonNull(fileName).substring(fileName.indexOf('.'), fileName.length());
                //判断文件是否是支持的图片格式类型
                if (Arrays.asList(imgType).contains(fileType))
                    return Result.result(false, null, "图片格式错误，上传失败 -_-");
                //设置新的文件名:不加根路径
                String newImgPath = path + "/" + new Date().getTime() + (long) (Math.random() * range) + fileType;
                //保存文件到存储服务器
                File newImg = new File(newImgPath);
                multipartFile.transferTo(newImg);
                //保存新文件路径
                newImgPaths[index++] = newImgPath;
            }
        }
        //上传图片到存储服务器
        return Result.result(true, newImgPaths, "图片上传成功 ^_^");
    }

    //删除图片
    public boolean removeFiles(String... imgPaths) {
        return Arrays.stream(imgPaths).map(s -> {
            File file = new File(s);
            if (!file.exists() || file.isDirectory())
                return false;
            return file.delete();
        }).toList().contains(false);
    }
}



