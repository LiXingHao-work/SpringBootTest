package com.example.test20240307.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.test20240307.pojo.Result;
import com.example.test20240307.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginChekInterceptor implements HandlerInterceptor {
    @Override//目标资源方法运行前运行,返回true: 放行, 返回false: 不放行
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        //1.获取请求url
        String url = req.getRequestURL().toString();
        log.info("请求的url: {}", url);

        //2.判断请求url中是否包含login,如果包含,说明是登陆操作,放行
        if(url.contains("login")){
            return true;
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
            return false;
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
            return false;
        }

        //6.放行
        log.info("令牌合法");
        return true;
    }

    @Override//目标资源方法运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override//视图渲染完毕后运行, 最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
