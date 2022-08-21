package com.yyq.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {

    User queryById(@Param("id") Integer id);


}
