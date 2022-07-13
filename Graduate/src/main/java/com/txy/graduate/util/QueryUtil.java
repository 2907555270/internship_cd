package com.txy.graduate.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询工具
 *    封装一些查询时用的小工具，主要功能有：
 *      1. obj2map | map2obi ：对象与Map之间的相互转换
 *      2. queryMapper_LikeMany : 根据传入对象的类型和属性，自动封装QueryWrapper的模糊查询条件，无需手动设置条件
 *
 */
public class QueryUtil {

    /**
     * 根据分页信息，构造map
     */
    public static Map<String,Object> getMapFromPage(int currentPage,int pageSize){
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPage",currentPage);
        map.put("pageSize",pageSize);
        return map;
    }

    /**
     * 从map提取分页信息，并封装为Page
     */
    public static <T> Page<T> getPageFromMap(Map<String,Object> map){
        Integer currentPage = (Integer) map.get("currentPage");
        Integer pageSize = (Integer) map.get("pageSize");
        if(!(currentPage!=null&&pageSize!=null)) {
            currentPage = 1;
            pageSize = 1;
        }
        return new Page<>(currentPage,pageSize);
    }

    /**
     * 将数据对象映射为map
     */
    public static Map<String,Object> obj2map(Object obj){
        HashMap<String, Object> map = new HashMap<>();
        //判断obj是否为空
        if(obj==null){
            return map;
        }
        //获取所有的成员变量
        Field[] fields = obj.getClass().getDeclaredFields();
        //暴力反射成员变量，封装到map中
        for (Field field : fields) {
            //设置暴力反射
            field.setAccessible(true);
            //数据封装
            try {
                Object attr = field.get(obj);
                if(attr!=null)
                    map.put(field.getName(),attr);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.out.println("对象封装为map时格式转换错误");
            }
        }
        return map;
    }

    /**
     * 将 map反射为数据对象
     */
    public static <T> T map2obj(Map<String,Object> map,Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //通过反射机制创建一个T对象
        T t = tClass.getConstructor().newInstance();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field:fields ) {
            field.setAccessible(true);
            //获取属性在map中的对应值
            Object attr = map.get(field.getName());
            //若没有获取到，则不注入属性
            if(attr!=null){
                //属性类型强制转换
                Object convertValue = ConvertUtils.convert(attr, field.getType());
                //设置属性
                field.set(t,convertValue);
            }
        }
        return t;
    }

    /**
     * 对wrapper进一步封装，使其能够自动添加多个模糊查询条件
     */
    public static <T> QueryWrapper<T> queryWrapper_LikeMany(T obj){
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if(obj==null)
            return wrapper;

        //暴力反射获取成员变量信息
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if(field.get(obj)!=null) {
                    //将对象属性名进行调整：驼峰命名-->短横线命名
                    String name = field.getName();
                    char[] chars = name.toCharArray();
                    for (char aChar : chars) {
                        if (Character.isUpperCase(aChar)) {
                            name = name.replaceFirst("" + aChar, "_" + Character.toLowerCase(aChar));
                        }
                    }
                    //添加模糊查询条件
                    wrapper.like(name, field.get(obj));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.out.println("类型转换错误，请检查对象是否为空");
            }
        }
        return wrapper;
    }
}
