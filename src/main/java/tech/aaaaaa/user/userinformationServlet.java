package tech.aaaaaa.user;

import com.alibaba.fastjson.JSON;
import tech.aaaaaa.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "/userinformationServlet", value = "/userinformationServlet")
public class userinformationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        User user=new User();
        user.setUid(1);
        user.setDescribes("这里是简介~");
        user.setUsername("管理员");
        user.setUpassword("123456");
        user.setEmail("111@aaaaaa.tech");
        user.setUgid(1);
        user.setRegistertime("2022-11-21 13:31:00");
        user.setIfconfirmemail(1);
        String jsonStr= JSON.toJSONString(user);
        PrintWriter writer= null;
        writer=response.getWriter();
        writer.print(jsonStr);
        writer.flush();
        response.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        this.doGet(request, response);
    }
}
