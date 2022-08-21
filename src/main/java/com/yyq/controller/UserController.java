package com.yyq.controller;

import com.yyq.entity.ResponseData;
import com.yyq.entity.User;
import com.yyq.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@Api(tags = "用户模块")
@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user
     * @return
     */
    @ApiOperation(value = "登录",notes = "返回token")
    @PostMapping("/login")
    public ResponseData login(@RequestBody User user){
        //加密
        String password = user.getPassword();
        Md5Hash md5Hash = new Md5Hash(password, "yaoyiqiang", 10);
        password = md5Hash.toString();
        System.out.println(user);
        return userService.login(user.getPhone(), password);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @ApiOperation(value = "注册",notes = "插入一条数据")
    @PostMapping("/register")
    public ResponseData register(@RequestBody User user){
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), "yaoyiqiang", 10);
        user.setPassword(md5Hash.toString());
        user.setCreateTime(LocalDateTime.now());
        System.out.println(user);
        return userService.register(user);
    }

    /**
     * 忘记密码
     * @param phone
     * @return
     */
    @ApiOperation(value = "忘记密码",notes = "查询一条数据并解密")
    @GetMapping("/forget/{phone}")
    public ResponseData forget(@PathVariable("phone") String phone){
        return userService.forget(phone);
    }


    @ApiOperation(value = "加载用户信息",notes = "解析token，查询一条信息")
    @GetMapping("/queryByToken")
    public ResponseData queryByToken(HttpServletRequest request){
        String token = request.getHeader("token");
        JwtParser parser = Jwts.parser();
        //验证密码
        parser.setSigningKey("yaoyiqiang");
        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String phone = body.get("phone", String.class);
        return userService.queryByToken(phone);
    }

}
