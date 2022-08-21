package com.yyq.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyq.entity.ResponseData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckToken implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //预检机制，放行
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }

        String token = request.getHeader("token");
        if (token == null){
            ResponseData responseData = new ResponseData("9999", "请先登录");
            toRes(response, responseData);
            return false;
        }else {
            try {
                JwtParser parser = Jwts.parser();
                //验证密码
                parser.setSigningKey("yaoyiqiang");
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            }catch (Exception e) {
                ResponseData responseData = new ResponseData("9998", "请重新登录");
                toRes(response, responseData);
            }
        }
        return false;
    }

    private void toRes(HttpServletResponse response, ResponseData responseData) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(responseData);
        writer.print(s);
        writer.flush();
        writer.close();

    }
}
