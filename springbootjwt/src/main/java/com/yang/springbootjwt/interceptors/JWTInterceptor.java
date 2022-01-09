package com.yang.springbootjwt.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.springbootjwt.Utils.JwtUtil.JwtUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: JWT认证拦截器
 * @Author: Guo.Yang
 * @Date: 2022/01/09/22:55
 */
public class JWTInterceptor  implements HandlerInterceptor {

    /**
     * 实现预处理方法即可
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 一般的token都会在请求头中
        String token = request.getHeader("token");

        Map<String , Object> map = new HashMap<>();
        try {
            if (StringUtils.isEmpty(token)) {
                 map.put("msg", "token不能为空");
            }else {
                JwtUtil.checkToken(token);
                return true;
            }
        }catch (SignatureVerificationException signatureVerificationException) {
            signatureVerificationException.printStackTrace();
            map.put("msg", "无效签名！");
        }catch (TokenExpiredException tokenExpiredException) {
            tokenExpiredException.printStackTrace();
            map.put("msg", "token过期");
        }catch (AlgorithmMismatchException algorithmMismatchException) {
            algorithmMismatchException.printStackTrace();
            map.put("msg", "token算法不一致");
        }catch (InvalidClaimException invalidClaimException) {
            invalidClaimException.printStackTrace();
            map.put("msg", "token无效");
        }catch (Exception e) {
            e.printStackTrace();
        }
        map.put("state",false);
        // 如果验证失败，需要将失败信息（map）以json的形式放在response中，响应给前台
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return false;
    }
}
