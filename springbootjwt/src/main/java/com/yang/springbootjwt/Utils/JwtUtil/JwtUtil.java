package com.yang.springbootjwt.Utils.JwtUtil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @Description: JWT工具类
 * @Author: Guo.Yang
 * @Date: 2022/01/09/21:04
 */
public class JwtUtil {

    // 指定的加密言
    private static final String SIGN = "1qaz!QAZ";

    /**
     * @Description: 通过消费段传进来的数据，生成对应的token令牌
     * @Param: Map<String,String> map
     * @return: String token
     * @Date: 2022/01/09 21:18:59
     */
    public static String getToken(Map<String,String> map){
        // 使用日历工具类，创建过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR , 3); // 默认三个小时token过期

        // 创建JWT buider
        JWTCreator.Builder builder = JWT.create();

        // 通过 foreach 表达式循环取出消费端传进来的数据信息，并将其存入token的有效负载中
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SIGN));
        return token;
    }


    /**
     * @Description: 验证token,并返回token信息
     * @Date: 2022/01/09 21:21:21
     */
    public static DecodedJWT checkToken(String token){
            DecodedJWT verify = JWT
                    .require(Algorithm.HMAC256(SIGN)).build() // 验签名
                    .verify(token);// 验证整体token的合法性
            return verify;
    }
    
}
