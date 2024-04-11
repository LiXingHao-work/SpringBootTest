package com.example.test20240307.controller;

import com.example.test20240307.anno.Log;
import com.example.test20240307.pojo.Dept;
import com.example.test20240307.pojo.Result;
import com.example.test20240307.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  部门控制器
 *
 */

@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping
//    @RequestMapping(value = "depts",method = RequestMethod.GET)
    public Result list(){
        log.info("查询全部部门数据");

        //调用service查询部门数据
        List<Dept> deptList = deptService.list();

        return Result.success(deptList);
    }


    /**
     * 删除部门
     * @return
     */
    @Log
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除部门:{}",id);

        // 调用service删除部门
        deptService.delete(id);
        return Result.success();
    }

    /**
     * 添加部门
     * @return
     */
    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门: {}" , dept);
        //调用service新增部门
        deptService.add(dept);
        return Result.success();
    }
}
