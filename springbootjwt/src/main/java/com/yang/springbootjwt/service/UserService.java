package com.yang.springbootjwt.service;

import com.yang.springbootjwt.entity.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/01/09/22:01
 */
@Service("UserService")
@Component
public interface UserService {
    UserDTO login(UserDTO userDto); // 登陆接口
}
