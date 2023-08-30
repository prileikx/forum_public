package tech.aaaaaa.user;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import jakarta.mail.MessagingException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.util.SendMailUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;


import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;

@WebServlet(name = "sendfindpwdemailServlet", value = "/sendfindpwdemailServlet")
public class sendfindpwdemailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        request.getParameter("email");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        String email = request.getParameter("email");
        PrintWriter writer = response.getWriter();
        UserMapper User = sqlSession.getMapper(UserMapper.class);
        Integer sendmailbool = null;
        try {
            sendmailbool = SendMailUtil.SendMailUtilWithVerifycode(email, "论坛找回密码验证邮件", "您正在找回论坛密码,您的验证码为:");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("send=" + sendmailbool);
        if (sendmailbool > 0) {
            writer.print("{\"msg\":\"验证码发送成功,如果未收到请检查垃圾邮件箱\"}");
        } else if (sendmailbool == -1) {
            writer.print("{\"msg\":\"距离上次发送时间过短,请隔段时间后重试\"}");
        } else if (sendmailbool == -2) {
            writer.print("{\"msg\":\"邮箱格式不正确\"}");
        }
        sqlSession.close();
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        this.doGet(request, response);
    }
}
