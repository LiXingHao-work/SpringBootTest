package com.example.test20240307.service;

import com.example.test20240307.pojo.Dept;

import java.util.List;

//部门业务规则
public interface DeptService {
    /**
     * 查询全部部门数据
     * @return
     */
    List<Dept> list();

    /**
     * 删除指定id部门
     * @param id
     */
    void delete(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    void add(Dept dept);
}
