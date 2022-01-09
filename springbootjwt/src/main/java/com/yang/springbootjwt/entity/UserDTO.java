package com.yang.springbootjwt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/01/09/21:57
 */
@Data
@TableName("user")
public class UserDTO {
    private String id;
    private String name;
    private String password;
}
