package com.example.test20240307.service.Impl;

import com.example.test20240307.mapper.DeptMapper;
import com.example.test20240307.mapper.EmpMapper;
import com.example.test20240307.pojo.Dept;
import com.example.test20240307.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        deptMapper.deleteByid(id);

        empMapper.deleteByDeptId(id);

    }

    @Override
    public void add(Dept dept) {
        //补全创建时间和修改时间
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(dept);
    }
}
