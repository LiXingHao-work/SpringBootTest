package com.example.test20240307.controller;

import com.example.test20240307.anno.Log;
import com.example.test20240307.pojo.Emp;
import com.example.test20240307.pojo.PageBean;
import com.example.test20240307.pojo.Result;
import com.example.test20240307.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//员工管理控制器
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 按条件查询员工
     * @param page
     * @param pageSize
     * @param name
     * @param gender
     * @param begin
     * @param end
     * @return
     */

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-mm-dd")LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-mm-dd")LocalDate end){
        log.info("分页查询,参数: {},{},{},{},{},{}",page,pageSize, name, gender ,begin, end);
        //调用service分页查询
        PageBean pageBean = empService.page(page,pageSize, name, gender ,begin, end);
        return Result.success(pageBean);
    }

    /**
     * 删除员工
     * @param ids
     * @return
     */
    @Log
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除操作,ids:{}",ids);
        empService.delete(ids);
        return Result.success();
    }

    /**
     * 新增员工
     * @param emp
     * @return
     */
    @Log
    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("新增员工, emp:{}", emp);
        empService.save(emp);
        return Result.success();
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据id查询员工信息,id: {}", id);
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    /**
     * 更新员工
     * @param emp
     * @return
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("更新员工信息 :{}", emp);
        empService.update(emp);
        return Result.success();
    }

}
