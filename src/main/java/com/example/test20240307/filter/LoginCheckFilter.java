package com.example.test20240307.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.test20240307.pojo.Result;
import com.example.test20240307.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //1.获取请求url
        String url = req.getRequestURL().toString();
        log.info("请求的url: {}", url);

        //2.判断请求url中是否包含login,如果包含,说明是登陆操作,放行
        if(url.contains("login")){
            chain.doFilter(request,response);
            return;
        }

        //3.获取请求头中的令牌(tooken)
        String jwt = req.getHeader("token");

        //4.判断令牌是否存在,如果不存在,返回错误结果(未登录)
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头为空,返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象=>json ----->  阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);//响应未登陆的结果给浏览器
            return;
        }

        //5.解析token,如果解析失败,返回错误结果(touken)
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {//jwt令牌解析失败
            e.printStackTrace();
            log.info("解析令牌失败,返回未登陆的错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象=>json ----->  阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);//响应未登陆的结果给浏览器
            return;
        }

        //6.放行
        log.info("令牌合法");
        chain.doFilter(request,response);

    }
}
