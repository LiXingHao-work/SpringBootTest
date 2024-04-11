package com.example.test20240307.mapper;

import com.example.test20240307.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     * 查询全部数据
     * @return
     */
    @Select("select * from dept")
    List<Dept> list();

    /**
     * 按照id删除部门
     * @param id
     */
    @Delete("delete from dept where id = #{id}")
    void deleteByid(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    @Insert("insert into dept(name , create_time , update_time) values (#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);
}
