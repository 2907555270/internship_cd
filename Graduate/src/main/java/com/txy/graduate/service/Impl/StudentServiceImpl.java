package com.txy.graduate.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.dto.StudentDto;
import com.txy.graduate.domain.po.*;
import com.txy.graduate.mapper.*;
import com.txy.graduate.service.StudentService;
import com.txy.graduate.util.QueryUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentService {

    @Resource(name = "studentInfoMapper")
    private StudentInfoMapper studentInfoMapper;

    @Resource(name = "studentDetailMapper")
    private StudentDetailMapper studentDetailMapper;

    @Resource(name = "bookInfoMapper")
    private BookInfoMapper bookInfoMapper;

    @Resource(name = "sysUserMapper")
    private SysUserMapper sysUserMapper;

    @Resource(name = "sysRoleMapper")
    private SysRoleMapper sysRoleMapper;

    //学生权限
    @Value("normal")
    private String code;


    /**
     * 查询
     */
    
    @Override
    //查询所有的学生的基础信息：导出为excel
    public List<StudentDto> queryAll(String schoolCode) {
        return studentInfoMapper.selectAll(schoolCode);
    }

    
    @Override
    //分页查询所有学生的基础信息
    public IPage<StudentInfo> queryByPage(String schoolCode, int currentPage, int pageSize) {
        //构建分页信息
        Page<StudentInfo> infoPage = new Page<>(currentPage, pageSize);

        //构建查询条件
        QueryWrapper<StudentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("school_code", schoolCode);

        Page<StudentInfo> studentInfoPage = studentInfoMapper.selectPage(infoPage, wrapper);

        //查询对应的完成状态
        studentInfoPage.getRecords().forEach(s -> s.setGraduationStatus(
                studentDetailMapper.selectOne(
                        new QueryWrapper<StudentDetail>()
                                .eq("id_card", s.getIdCard())
                                .select("graduation_status")
                ).getGraduationStatus()
        ));

        return studentInfoPage;
    }

    
    @SneakyThrows
    @Override
    //分页多条件查询所有学生的基础信息
    public Page<List<Map<String, Object>>> queryByConditionsAndPage(Map<String, Object> map, String schoolCode, int currentPage, int pageSize) {

        //构建分页条件
        Page<StudentInfo> infoPage = new Page<>(currentPage, pageSize);

        //构建查询条件
        StudentInfo studentInfo = QueryUtil.map2obj(map, StudentInfo.class);
        QueryWrapper<StudentInfo> wrapper = QueryUtil.queryWrapper_LikeMany(studentInfo);
        wrapper.eq("school_code", schoolCode);

        //是否有输入内容
        if (map.get("content") != null)
            wrapper.and(studentInfoQueryWrapper -> studentInfoQueryWrapper
                    .like("student_id", map.get("content"))
                    .or()
                    .like("student_name", map.get("content")));

        Page<StudentInfo> studentInfoPage = studentInfoMapper.selectPage(infoPage, wrapper);

        //查询对应的完成状态
        List<Map<String, Object>> result = new ArrayList<>();
        studentInfoPage.getRecords().forEach(s -> {
                    StudentDetail studentDetail = studentDetailMapper.selectOne(
                            new QueryWrapper<StudentDetail>()
                                    .eq("id_card", s.getIdCard())
                                    .select("graduation_status", "report_status", "report_time", "audit_status", "owe_status", "book_status",
                                            "archives_status", "archives_time", "dormitory_status", "diploma_status", "diploma_time")
                    );

                    //查询bookStatus
                    QueryWrapper<BookInfo> bookWrapper = new QueryWrapper<>();
                    bookWrapper.eq("id_card", s.getIdCard());
                    BookInfo bookInfo = bookInfoMapper.selectOne(bookWrapper);

                    //修改bookStatus
                    HashMap<String, Object> bookMap = new HashMap<>();
                    bookMap.put("id", s.getId());
                    if (bookInfo.getBookAmount() > 0 || bookInfo.getOweAmount().intValue() > 0) {
                        bookMap.put("bookStatus", 0);
                        studentDetail.setBookStatus((byte) 0);
                    } else {
                        //已完成，修改数据库数据
                        bookMap.put("bookStatus", 1);
                        //设置返回结果
                        studentDetail.setBookStatus((byte) 1);
                    }
                    updateStatus(bookMap);


                    s.setGraduationStatus(studentDetail.getGraduationStatus());

                    //封装为map
                    HashMap<String, Object> m = new HashMap<>();
                    m.put("studentInfo", s);
                    m.put("studentDetail", studentDetail);
                    result.add(m);
                }
        );

        //新建一个分页对象
        Page<List<Map<String, Object>>> page = new Page<>();
        page.setRecords(Collections.singletonList(result));
        page.setTotal(studentInfoPage.getTotal());
        page.setSize(studentInfoPage.getSize());
        page.setCurrent(studentInfoPage.getCurrent());
        page.setPages(studentInfoPage.getPages());

        return page;
    }

    
    @Override
    //对内容按学号和姓名查找
    public IPage<StudentInfo> queryByIdOrName(String content, String schoolCode) {

        QueryWrapper<StudentInfo> wrapper = new QueryWrapper<>();

        wrapper.select("id", "student_id", "student_name", "student_class")
                .eq("school_code", schoolCode)
                .and(studentInfoQueryWrapper -> studentInfoQueryWrapper
                        .like("student_id", content)
                        .or()
                        .like("student_name", content));

        //只查询前5条数据
        Page<StudentInfo> infoPage = new Page<>(1, 5);

        return studentInfoMapper.selectPage(infoPage, wrapper);
    }

    @Override
    
    public Map<String, Object> queryById(Long id) {
        //查询学生的身份信息
        QueryWrapper<StudentInfo> infoWrapper = new QueryWrapper<>();
        infoWrapper.select("id", "student_id", "student_name", "student_dep", "student_pre", "student_class", "id_card")
                .eq("id", id);
        StudentInfo studentInfo = studentInfoMapper.selectOne(infoWrapper);

        //查询学生的状态信息
        QueryWrapper<StudentDetail> detailWrapper = new QueryWrapper<>();
        detailWrapper.select("graduation_status", "report_status", "report_time", "audit_status", "owe_status", "book_status",
                        "archives_status", "dormitory_status", "diploma_status", "diploma_time")
                .eq("id_card", studentInfo.getIdCard());
        StudentDetail studentDetail = studentDetailMapper.selectOne(detailWrapper);

        //查询学生的图书欠款、待还信息
        QueryWrapper<BookInfo> bookWrapper = new QueryWrapper<>();
        bookWrapper.select("owe_amount", "book_amount")
                .eq("id_card", studentInfo.getIdCard());
        BookInfo bookInfo = bookInfoMapper.selectOne(bookWrapper);
        if (bookInfo.getBookAmount() > 0 || bookInfo.getOweAmount().intValue() > 0)
            studentDetail.setBookStatus((byte) 0);
        else {
            //已完成，修改数据库数据
            HashMap<String, Object> bookMap = new HashMap<>();
            bookMap.put("id", id);
            bookMap.put("bookStatus", 1);
            updateStatus(bookMap);
            //设置返回结果
            studentDetail.setBookStatus((byte) 1);
        }

        //封装上述信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("studentInfo", studentInfo);
        map.put("studentDetail", studentDetail);

        return map;
    }

    @Override
    
    public Map<String, Object> queryNav(String schoolCode) {
        // 查询专业导航信息
        Map<String, Object> map = new HashMap<>();
        HashSet<String> setDep = new HashSet<>();
        HashSet<String> setPre = new HashSet<>();
        HashSet<String> setClass = new HashSet<>();

        List<StudentInfo> studentInfos = studentInfoMapper.selectNav(schoolCode);
        studentInfos.forEach(s -> {
            setDep.add(s.getStudentDep());
            setPre.add(s.getStudentPre());
            setClass.add(s.getStudentClass());
        });

        map.put("studentDep", setDep);
        map.put("studentPre", setPre);
        map.put("studentClass", setClass);
        return map;
    }

    @Override
    
    public Map<String, Object> queryPartInfoById(Map<String, Object> map) {
        if (map.isEmpty())
            return null;

        if (map.get("id") == null)
            return null;

        Long id = Long.parseLong(map.get("id").toString());
        //查询学生的基本信息
        StudentInfo sInfo = studentInfoMapper.selectById(id);
        if (sInfo == null)
            return null;
        String idCard = sInfo.getIdCard();

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("studentName", sInfo.getStudentName());
        result.put("studentDep", sInfo.getStudentId());
        result.put("studentClass", sInfo.getStudentClass());

        QueryWrapper<StudentDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("id_card", idCard);

        //获取报到证领取状态相关信息
        if (map.get("reportStatus") != null) {
            //查询报到证领取状态
            wrapper.select("report_status", "report_note", "report_time");
            result.put("reportInfo", studentDetailMapper.selectOne(wrapper));
        }

        //获取毕业审核状态相关信息
        if (map.get("auditStatus") != null) {
            //查询毕业审核状态
            wrapper.select("audit_status", "audit_note");
            result.put("auditInfo", studentDetailMapper.selectOne(wrapper));
        }

        //获取欠费缴纳状态相关信息
        if (map.get("oweStatus") != null) {
            //查询欠款状态和欠款金额
            wrapper.select("owe_status", "owe_amount", "owe_note");
            StudentDetail studentDetail = studentDetailMapper.selectOne(wrapper);
            result.put("oweInfo", studentDetail);
        }

        //获取图书归还相关信息
        if (map.get("bookStatus") != null) {
            HashMap<String, Object> m = new HashMap<>();
            //查询图书归还状态
            wrapper.select("book_status", "book_note");
            m.putAll(QueryUtil.obj2map(studentDetailMapper.selectOne(wrapper)));
            //查询图书欠款 + 图书未还数量
            m.putAll(QueryUtil.obj2map(studentInfoMapper.selectBookInfoById(id)));
            result.put("bookInfo", m);
        }

        //获取档案去向信息
        if (map.get("archivesStatus") != null) {
            //查询档案确认状态和地址去向+备注信息
            wrapper.select("archives_status", "archives_address", "archives_note", "archives_time");
            result.put("archivesInfo", studentDetailMapper.selectOne(wrapper));
        }

        //获取公寓退宿状态信息
        if (map.get("dormitoryStatus") != null) {
            //查询公寓退宿状态
            wrapper.select("dormitory_status", "dormitory_note");
            result.put("dormitoryInfo", studentDetailMapper.selectOne(wrapper));
        }

        //获取毕业证的发放状态
        if (map.get("diplomaStatus") != null) {
            //获取毕业证发放状态
            wrapper.select("diploma_status", "diploma_time", "diploma_note");
            result.put("diplomaInfo", studentDetailMapper.selectOne(wrapper));
        }

        return result;
    }

    @SneakyThrows
    @Override
    public boolean updateStatus(Map<String, Object> map) {
        if (map.get("id") == null)
            return false;

        //只保留和修改状态相关的属性
        map.forEach((k, v) -> {
            if (!k.equals("id") && !k.contains("Status")) {
                map.remove(k);
            }
        });

        // 根据学生的id查询学生的id_card
        QueryWrapper<StudentInfo> wrapper = new QueryWrapper<>();
        wrapper.select("id_card").eq("id", map.get("id"));
        map.remove("id");
        String idCard = studentInfoMapper.selectOne(wrapper).getIdCard();

        //清洗之后map中还含有除id的其他键值对
        StudentDetail studentDetail = QueryUtil.map2obj(map, StudentDetail.class);

        QueryWrapper<StudentDetail> detailWrapper = new QueryWrapper<>();
        detailWrapper.eq("id_card", idCard);
        return studentDetailMapper.update(studentDetail, detailWrapper) > 0;
    }

    
    @Override
    public Map<String, Map<String, Object>> queryStatus(StudentInfo studentInfo) {
        //根据条件查询需要统计的用户的 id_card
        QueryWrapper<StudentInfo> infoWrapper = QueryUtil.queryWrapper_LikeMany(studentInfo).select("id_card");
        List<String> idCards = studentInfoMapper.selectList(infoWrapper).stream().map(StudentInfo::getIdCard).collect(Collectors.toList());

        //统计各流程的备注信息
        QueryWrapper<StudentDetail> detailWrapper = new QueryWrapper<>();
        detailWrapper.eq("id", 1)
                .select("report_note", "audit_note", "owe_note", "book_note", "archives_note",
                        "dormitory_note", "diploma_note");
        StudentDetail studentDetail = studentDetailMapper.selectOne(detailWrapper);

        //统计各流程的备注人数
        Map<String, BigDecimal> noteCounts = studentDetailMapper.selectNotes(idCards);
        //统计各流程的各状态的人数
        Map<String, BigDecimal> map = studentDetailMapper.selectStatus(idCards);
        Map<String, Map<String, Object>> maps = new HashMap<>();
        map.forEach((k, v) -> {
            HashMap<String, Object> m = new HashMap<>();
            m.put("total", idCards.size());
            m.put("completed", v);
            m.put("unfinished", idCards.size() - v.intValue());
            m.put("noteNum", noteCounts.get(k));

            //获取备注信息
            String note = null;
            char[] chars = k.toCharArray();
            for (char c : chars) {
                if (Character.isUpperCase(c)) {
                    try {
                        String name = k.substring(0, k.indexOf(c)) + "Note";
                        Field field = studentDetail.getClass().getDeclaredField(name);
                        field.setAccessible(true);
                        note = (String) field.get(studentDetail);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            m.put("note", note);

            maps.put(k, m);
        });

        return maps;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    
    @Override
    public StudentInfo queryByStudentId(String studentId) {
        QueryWrapper<StudentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        wrapper.select("id");
        return studentInfoMapper.selectOne(wrapper);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean saveStudent(StudentInfo student) {

        //添加学生student信息
        boolean flag1 = studentInfoMapper.insert(student) > 0;

        //添加学生用户user信息
        SysUser sysUser = new SysUser();
        sysUser.setUsername(student.getStudentId());
        sysUser.setPassword(student.getStudentId());
        boolean flag2 = sysUserMapper.insert(sysUser) > 0;

        //根据学生对应的权限code查询权限id
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        wrapper.select("id");
        Long role_id = sysRoleMapper.selectOne(wrapper).getId();

        //为学生用户user设置权限
        SysUserRole sysUserRole = new SysUserRole();
        //上面插入user的主键id会被自动封装到对象中
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(role_id);
        //todo：需要将SysUserRole单独分出去一个Mapper
        boolean flag3 = sysRoleMapper.insertUserAndRole(sysUserRole) > 0;

        return flag1 && flag2 && flag3;
    }

    //需要抛出异常才能触发此处的事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result removeStudent(Long id) {

        //根据学生id去查询学生的全部信息
        QueryWrapper<StudentInfo> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.select("student_id");
        studentQueryWrapper.eq("id", id);
        StudentInfo student = studentInfoMapper.selectOne(studentQueryWrapper);
        if (student == null)
            return Result.result(500, false, null, "未找到该学号对应的学生信息 -_-");
        String studentId = student.getStudentId();

        //根据学生id信息查询用户id
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select("id");
        userQueryWrapper.eq("username", studentId);
        Long userId = sysUserMapper.selectOne(userQueryWrapper).getId();

        //解绑学生的权限信息
        //todo:需要将SysUserRole分出去一个Mapper
        if (!(sysRoleMapper.deleteUserAndRoleByUId(userId) > 0))
            return Result.result(500, false, null, "解绑学生的权限信息失败 -_-");

        //删除学生账号
        if (!(sysUserMapper.deleteByUserName(studentId) > 0))
            return Result.result(500, false, null, "删除学生账号失败 -_-");


        //删除学生student信息
        if (!(studentInfoMapper.deleteById(id) > 0))
            return Result.result(500, false, null, "删除学生信息失败 -_-");

        return Result.result(200, true, null, "删除成功 ^_^");
    }
}
