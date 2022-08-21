package com.yyq.service;

import com.yyq.entity.ResponseData;
import com.yyq.entity.User;

public interface UserService {

    ResponseData login(String phone, String password);

    ResponseData register(User user);

    ResponseData forget(String phone);

    ResponseData queryByToken(String phone);

    ResponseData webSocket(String phone, String webSocketCode, String webSocketMsg);

}
