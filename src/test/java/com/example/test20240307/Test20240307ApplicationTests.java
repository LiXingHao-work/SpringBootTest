package com.example.test20240307;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
class Test20240307ApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 生成JWT令牌
     */
    @Test
    public void testGenJwt(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("name", "tom");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "lixinghao")//设置签名算法
                .setClaims(claims)//自定义内容(载荷)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))//设置JWT令牌有效期为1小时
                .compact();
        System.out.println(jwt);
    }

    /**
     * 解析数据
     */
//    @Test
//    public void testParseJwt(){
//        Claims claims = Jwts.parser()
//                .setSigningKey("lixinghao")
//                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9tIiwiaWQiOjEsImV4cCI6MTcxMDIzNDUzNH0.rsXpTsGqSAtDvmFfmQmpxBo5YlJciybNU2RrsungxcY")
//                .getBody();
//        System.out.println(claims);
//    }
}
