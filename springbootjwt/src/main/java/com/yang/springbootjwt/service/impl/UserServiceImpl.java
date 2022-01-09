package com.yang.springbootjwt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yang.springbootjwt.dao.UserDao;
import com.yang.springbootjwt.entity.UserDTO;
import com.yang.springbootjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/01/09/22:00
 */
@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDTO login(UserDTO userDto) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", userDto.getName());
        queryWrapper.eq("password",userDto.getPassword());
        UserDTO userDTO = userDao.selectOne(queryWrapper);

        if(userDTO != null){
            return userDTO;
        }
        throw new RuntimeException("登陆失败！");
    }
}
