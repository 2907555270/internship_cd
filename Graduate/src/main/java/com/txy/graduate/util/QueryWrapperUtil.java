package com.txy.graduate.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 对Mybatis-plus的QueryWrapper进一步封装，增强功能
 */
public class QueryWrapperUtil {

    /**
     * 将数据对象映射为map
     * @param obj
     * @return
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
                map.put(field.getName(),field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.out.println("对象封装为map时格式转换错误");
            }
        }
        return map;
    }

    /**
     * 将 map反射为数据对象
     * @param map
     * @param tClass
     * @param <T>
     */
    public static <T> T map2obj(Map<String,Object> map,Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //通过反射机制创建一个T对象
        T t = tClass.getConstructor().newInstance();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field:fields ) {
            field.setAccessible(true);
            field.set(t,map.get(field.getName()));
        }
        return t;
    }

    /**
     * 对wrapper进一步封装，使其能够自动添加多个模糊查询条件
     * @param obj
     * @param <T>
     * @return
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
