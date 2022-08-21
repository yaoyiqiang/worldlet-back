package com.yyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yyq.dao.UserDao;
import com.yyq.entity.ResponseData;
import com.yyq.entity.User;
import com.yyq.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 登录
     * @param phone
     * @param password
     * @return
     */
    @Override
    public ResponseData login(String phone, String password) {
        //条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //等值查询
        queryWrapper.eq(User::getPhone, phone);

        try {
            User loginUser = userDao.selectOne(queryWrapper);

            //查询结果处理
            if (loginUser == null){
                return new ResponseData("9000", "手机号未注册");
            }else if (!password.equals(loginUser.getPassword())){
                System.out.println(password+"-------"+loginUser.getPassword());
                return new ResponseData("9001", "密码错误");
            }else {
                System.out.println("密码正确"+loginUser);
                HashMap<String, Object> map = new HashMap<>();
                map.put("phone",loginUser.getPhone());

                //生成token
                JwtBuilder jwtBuilder = Jwts.builder();
                String token = jwtBuilder
                        .setSubject(loginUser.getNickName())//主题
                        .setId(String.valueOf(loginUser.getId()))
                        .setClaims(map)
                        .setIssuedAt(new Date())//token生成时间
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                        .signWith(SignatureAlgorithm.HS256, "yaoyiqiang")
                        .compact();
                return new ResponseData("201", "登录成功", token);
            }
        } catch (Exception e) {
            return new ResponseData("0","网络异常");
        }
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public ResponseData register(User user) {
        //条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //等值查询
        queryWrapper.eq(User::getPhone, user.getPhone());

        try {
            User registerUser = userDao.selectOne(queryWrapper);

            //查询结果处理
            if (registerUser != null ) {
                return new ResponseData("9002", "手机号已注册");
            }else {
                userDao.insert(user);
                System.out.println(user);
                return new ResponseData("202", "注册成功");
            }
        } catch (Exception e) {
            return new ResponseData("0","网络异常");
        }
    }

    @Override
    public ResponseData forget(String phone) {
        //条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //等值查询
        queryWrapper.eq(User::getPhone,phone);

        try {
            User forgetUser = userDao.selectOne(queryWrapper);

            if (forgetUser == null){
                return new ResponseData("9000", "手机号未注册");
            }else {
                String password = forgetUser.getPassword();
                Md5Hash md5Hash = new Md5Hash(password, "yaoyiqiang", 10);
                password = md5Hash.toString();
                return new ResponseData("200", "请求成功", password);
            }
        } catch (Exception e) {
            return new ResponseData("0", "网络异常");
        }
    }

    @Override
    public ResponseData queryByToken(String phone) {
        //条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //等值查询
        queryWrapper.eq(User::getPhone,phone);

        try {
            User tokenUser = userDao.selectOne(queryWrapper);
            return new ResponseData("200", "请求成功", tokenUser);
        }catch (Exception e) {
            return new ResponseData("0", "网络异常");
        }
    }

    @Override
    public ResponseData webSocket(String phone, String webSocketCode, String webSocketMsg) {
        //条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //等值查询
        queryWrapper.eq(User::getPhone,phone);

        try {
            User webSockettUser = userDao.selectOne(queryWrapper);
            System.out.println(webSockettUser);
            return new ResponseData("88", "webSocketMsg", webSockettUser);
        } catch (Exception e) {
            return new ResponseData("0", "网络异常");
        }


    }

}
