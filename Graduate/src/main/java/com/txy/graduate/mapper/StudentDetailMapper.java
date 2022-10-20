package com.txy.graduate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txy.graduate.domain.po.StudentDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Mapper
public interface StudentDetailMapper extends BaseMapper<StudentDetail> {
    //统计各个状态的备注人数
    Map<String, BigDecimal> selectNotes(@Param("idCards") List<String> idCards);

    //统计各个状态的人数
    Map<String, BigDecimal> selectStatus(@Param("idCards") List<String> idCards);
}