package com.yyq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocket {
    private String nick;
    private String sex;
    private String head;
    private String code;
    private String msg;
    private String person;
    private String phone;
}
