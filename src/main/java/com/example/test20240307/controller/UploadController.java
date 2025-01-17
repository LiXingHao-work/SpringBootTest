package com.example.test20240307.controller;

import com.example.test20240307.pojo.Result;
import com.example.test20240307.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

//    @PostMapping("/upload")
//    public Result upload(String username, Integer age, MultipartFile image) throws Exception {
//        log.info("文件上传:{}, {}, {}", username, age, image);
//
//        //获取原始文件名
//        String originalFilename = image.getOriginalFilename();
//
//        //获取原始文件名(不能重复) - uuid(通用唯一识别码)拼接上文件的扩展名
//        int i = originalFilename.lastIndexOf(".");
//        String extname = originalFilename.substring(i);//获取文件扩展名
//        String newFileName = UUID.randomUUID().toString() + extname;
//        log.info("新的文件名: {}", newFileName);
//
//        //将文件存储在服务器本地磁盘目录"D:\Study\JavaWeb\24-3-7 image"
//        image.transferTo(new File("D:\\Study\\JavaWeb\\24-3-7 image\\newFileName"));
//
//        return Result.success();
//    }

    @PostMapping
    public Result upload(MultipartFile image) throws IOException {
        log.info("文件上传:{}", image.getOriginalFilename());
        //调用阿里云OSS工具类
        String url = aliOSSUtils.upload(image);
        log.info("文件上传完成,文件访问的url: {}", url);
        return Result.success(url);
    }

}
