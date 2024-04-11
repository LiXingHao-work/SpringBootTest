package com.example.test20240307.pojo;

import lombok.*;

import java.time.LocalDateTime;

/*部门类*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dept {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
