package com.txy.graduate.util;

import com.txy.graduate.config.Result;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Component("fileUtil")
public class FileUtil {

    @Value("${project.host}")
    private String HOST;

    @Value("${nginx.files.port}")
    private String PORT;

    @Value("${img.root.path}")
    private String IMG_ROOT_PATH;

    @Value("${excel.root.path}")
    private String EXCEL_ROOT_PATH;

    //允许上传的图片格式
    private final String[] imgType = {"jpg", "png", "bmp", "gif"};

    //命名随机值的最大范围：如果经常出现重名，则将数值调大
    @Value("1000")
    private int range;


    //获取文件存储的根路径
    public <T> String getRootPath(Class<T> tClass) {
        return IMG_ROOT_PATH + tClass.getSimpleName();
    }

    //拼接访问文件的url
    public String getUrl(String fileName) {
        String baseUrl = "";
        if(!HOST.isEmpty()&&!PORT.isEmpty())
            baseUrl = HOST+":"+PORT;
        return (baseUrl + fileName.replaceAll("/opt/files",""));
    }

    //上传图片：支持的图片格式为：jpg,png,gif,bmp
    public <T> Result uploadPics(MultipartFile[] multipartFiles, Class<T> tClass) throws IOException {

        //获取图片存放的路径名：root_path/tClass_name/
        String path = getRootPath(tClass);

        ////初始化存储目录
        //if (!initDirectory(path)) {
        //    return Result.result(false, null, "文件目录初始化失败:" + path);
        //}

        //判断图片资源是否为空
        if (multipartFiles == null || multipartFiles[0].isEmpty()) {
            return Result.result(500,false, "图片内容为空，上传失败 -_-",null);
        }

        //遍历图片资源，并保存，然后返回路径
        String[] newImgPaths = new String[multipartFiles.length];
        int index = 0;
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                //获取文件后缀名
                String fileName = multipartFile.getOriginalFilename();
                String fileType = Objects.requireNonNull(fileName).substring(fileName.indexOf('.'), fileName.length());
                //判断文件是否是支持的图片格式类型
                if (Arrays.asList(imgType).contains(fileType))
                    return Result.result(500,false, "图片格式错误，上传失败 -_-", null);
                //设置新的文件名:不加根路径
                String newImgPath = path + "/" + new Date().getTime() + (long) (Math.random() * range) + fileType;
                //保存文件到存储服务器
                File newImg = new File(newImgPath);
                multipartFile.transferTo(newImg);
                //保存新文件路径
                newImgPaths[index++] = getUrl(newImgPath);
            }
        }
        //上传图片到存储服务器
        return Result.result(200,true, "图片上传成功 ^_^", newImgPaths);
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

    /**
     * 传入数据对象，生成并导出excel
     * args[0] = file_name
     * args[1] = sheet_name
     * 以集合中的第一个数据不为空的属性名作为表头
     *
     */
    public <T> Result exportDataToExcel(List<T> tList, String... args) {

        //默认参数
        //文件输出路径
        String fileName = EXCEL_ROOT_PATH + "export-" + new Date() + ".xls";
        //sheet名
        String sheetName = "sheet";
        //行指针
        int rowIndex = 0;
        //列指针
        int cellIndex = 0;

        //数据是否为空
        if (tList == null || tList.isEmpty()) {
            return Result.result(500,false,  "数据为空，导出失败 -_-",null);
        }

        //有参数则获取参数，并初始化参数
        if (args != null) {
            if (args.length >= 1 && args[0] != null)
                fileName = EXCEL_ROOT_PATH + args[0] + ".xls";
            if (args.length >= 2 && args[1] != null)
                sheetName = args[1];
        }

        ////初始化文件存储目录
        //if (!initDirectory(EXCEL_ROOT_PATH)) {
        //    return Result.result(false, null, "文件目录初始化失败 -_-:" + EXCEL_ROOT_PATH);
        //}

        //解析对象获取初始数据-获取对象的所有属性名
        List<String> filedNames = QueryUtil.getFiledNamesNotNull(tList.get(0));

        //创建一个Excel对应的对象
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建单元格样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        //创建一个sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        //设置单元格样式:水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        //数据写入表头
        HSSFRow row;
        row = sheet.createRow(rowIndex++);
        for (String rowName : filedNames) {
            row.createCell(cellIndex++).setCellValue(rowName);
        }

        //遍历写入数据
        for (T t : tList) {
            //将对象转换为map
            Map<String, Object> map = QueryUtil.obj2map(t);
            //指针归零
            cellIndex = 0;
            //创建新的一行
            row = sheet.createRow(rowIndex++);
            //写入一行数据
            for (String rowName : filedNames) {
                Object value = map.get(rowName);
                if (value != null)
                    row.createCell(cellIndex++).setCellValue(value.toString());
            }
        }

        //输出文件
        try (OutputStream fileOut = new FileOutputStream(fileName)) {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.result(500,false, "文件输出时错误 -_-",null);
        }

        return Result.result(200,true, getUrl(fileName),null);
    }
}


