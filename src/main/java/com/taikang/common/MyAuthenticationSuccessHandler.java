package com.taikang.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taikang.entity.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author itw_gongxy
 * @date 2020/2/27 15:18
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler{


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {

        response.setContentType("text/json;charset=utf-8");

        Result result = new Result();   
        result.setCode(200);
        result.setMsg("登陆成功");
        OutputStream out = response.getOutputStream();
        ObjectMapper om = new ObjectMapper();
        om.writeValue(out,result);
        out.flush();
        out.close();

    }
}
