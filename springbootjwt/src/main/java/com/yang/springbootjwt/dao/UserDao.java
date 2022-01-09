package com.yang.springbootjwt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.springbootjwt.entity.UserDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/01/09/21:57
 */
@Mapper
public interface UserDao extends BaseMapper<UserDTO> {
}
