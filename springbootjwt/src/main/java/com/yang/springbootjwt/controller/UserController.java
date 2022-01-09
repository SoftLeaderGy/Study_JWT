package com.yang.springbootjwt.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yang.springbootjwt.Utils.JwtUtil.JwtUtil;
import com.yang.springbootjwt.dao.UserDao;
import com.yang.springbootjwt.entity.UserDTO;
import com.yang.springbootjwt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/01/09/22:10
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登陆并生成token令牌
     * @param userDTO
     * @return
     */
    @GetMapping("/user/login")
    public Map<String,Object> login(UserDTO userDTO){
        log.info("用户名："+userDTO.getName());
        log.info("密码："+userDTO.getPassword());
        Map<String , Object> map  = new HashMap<>();
        try {
            UserDTO user = userService.login(userDTO);
            map.put("state",true);
            map.put("msg","认证成功");
            Map<String, String> payload = new HashMap<>();
            payload.put("userName", userDTO.getName());
            payload.put("userId",userDTO.getId());
            String token = JwtUtil.getToken(payload);
            map.put("token",token);
        }catch (Exception e){
            map.put("state",false);
            map.put("msg","认证失败");
        }
        return map;
    }

    /**
     * 令牌验证:根据令牌和签名解析数据
     * 常⻅异常：
     *   SignatureVerificationException 签名不一致异常
     *   TokenExpiredException          令牌过期异常
     *   AlgorithmMismatchException     算法不匹配异常
     *   InvalidClaimException          失效的payload异常
     */
    @PostMapping("/user/test")
    public Map<String,Object> test(){
        Map<String , Object> map = new HashMap<>();


        map.put("state",true);
        map.put("msg","请求成功");
        return map;
    }
}
