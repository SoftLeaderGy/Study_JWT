package com.yang.springbootjwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.yang.springbootjwt.Utils.JwtUtil.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

class SpringbootjwtApplicationTests {

    @Test
    void contextLoads() {
    }
/**
 * header可以不写有默认值
 * payload 通常用来存放用戶信息
 * signature 是前两个合起来的签名值
 *
 * token = header.payload.signature

 */
    /**
     * @Description: token令牌的获取
     * @Param:
     * @return:
     * @Date: 2022/01/09 20:07:36
     */
    @Test
    public void getToken() {
        HashMap<String,Object> map = new HashMap<>();
        // 使用日历类
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,100);

        String token = JWT.create()
//                .withHeader(map) // 头信息 指定token的类型（jwt） 也可以省略
                .withClaim("username","guoyang") // 有效负载 可存一些用户的基础信息，且是非敏感信息
                .withClaim("userId",21) // 可存多条
                .withExpiresAt(calendar.getTime()) // 指定token的过期时间
                .sign(Algorithm.HMAC256("1qaz!QAZ")); // 签名

        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDE3Mjk5MTEsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoiZ3VveWFuZyJ9.kRenu1XzumlRNcjiKKgEbKTI-L7EIrlCf9z-dyPB1Uc
        System.out.println(token);
    }


     /**
     * @Description: token令牌的验证
     * @Param:
     * @return:
     * @Date: 2022/01/09 20:42:38
     */
    @Test
    public void checkToken(){
        // 验签名 ，并创建验证对象
        JWTVerifier build = JWT.require(Algorithm.HMAC256("1qaz!QAZ")).build();
        DecodedJWT verify  = null;
        try {
            // 验证"getToken"方法创建的token串
            verify = build.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDE3MzIwODIsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoiZ3VveWFuZyJ9.eGF_10ojqm11JoeTyb2fhHkGJCNuklckzlmBCS3ecac");
        }catch (TokenExpiredException e) {
            throw new RuntimeException("登陆过期，请重新登陆！");
        }

        System.out.println(verify);
        // 可以根据验证的对象 的 getClaim方法获取到在创建token是放在负载里边的用户信息
        System.out.println(verify.getClaim("userId").asInt()); // 21
        System.out.println(verify.getClaim("username").asString()); // guoyang

        // 也可以回去token的过期时间
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(verify.getExpiresAt());
        System.out.println("过期时间："+ format); // 过期时间：2022-01-09 20:41:22
    }

    /**
     * 令牌验证:根据令牌和签名解析数据
     * 常⻅异常：
     *   SignatureVerificationException 签名不一致异常
     *   TokenExpiredException          令牌过期异常
     *   AlgorithmMismatchException     算法不匹配异常
     *   InvalidClaimException          失效的payload异常
     */

    @Test
    public void testToken(){
        HashMap<String , String> map = new HashMap<>();
        map.put("username","gy");
        map.put("userId", "123");
        String token = JwtUtil.getToken(map);
        System.out.println(token);

        DecodedJWT decodedJWT = JwtUtil.checkToken(token);
        System.out.println(decodedJWT.getClaim("username").asString());
        System.out.println(decodedJWT.getClaim("userId").asString());
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(decodedJWT.getExpiresAt());
        System.out.println("token 到期时间为："+format);
    }
}
