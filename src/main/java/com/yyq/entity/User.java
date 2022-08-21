package com.yyq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * user实体类
 * @Data get set
 * @NoArgsConstructor 无参构造
 * @AllArgsConstructor 有参构造
 * @TableName 表名
 * @Alias 全类名别名
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
@Alias("user")
@ApiModel(description = "登录")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String nickName;
    @ApiModelProperty(value = "手机号", name = "phone", required = true, example = "15623566510")
    private String phone;
    @ApiModelProperty(value = "密码", name = "password", required = true, example = "123456")
    private String password;
    private String sex;
    private LocalDateTime createTime;
    private String headUrl;
}
