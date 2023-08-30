package tech.aaaaaa.user;


import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.CaptchaMapper;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.CheckEmailFormatUtil;
import tech.aaaaaa.util.PasswordsaltUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import java.io.PrintWriter;

@WebServlet(name = "findpasswordServlet", value = "/findpasswordServlet")
public class findpasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        //获取验证码的值
        String verify_code = request.getParameter("verify_code");
        HttpSession session = request.getSession();
        String checkcode = (String) session.getAttribute("checkcodegen");
        PrintWriter writer = response.getWriter();
        if (checkcode == null) {
            writer.print("{\"msg\":\"我们无法比对此验证码,请点击'看不清?换一张'重新获取验证码输入\"}");
            writer.flush();
            writer.close();
            return;
        }
        //比对验证码
        if (!checkcode.equalsIgnoreCase(verify_code)) {
            writer.print("{\"msg\":\"验证码不正确\"}");
            writer.flush();
            writer.close();
            return;
        }
        String email = request.getParameter("email");
        if (email == null) {
            writer.print("{\"msg\":\"邮箱不能为空\"}");
            writer.flush();
            writer.close();
            return;
        }
        String email_verify = request.getParameter("email_verify");
        if (email_verify == null) {
            writer.print("{\"msg\":\"邮箱验证码不能为空\"}");
            writer.flush();
            writer.close();
            return;
        }
        String upassword = request.getParameter("upassword");
        if (upassword == null) {
            writer.print("{\"msg\":\"密码不能为空\"}");
            writer.flush();
            writer.close();
            return;
        }
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        CaptchaMapper captchaMapper = sqlSession.getMapper(CaptchaMapper.class);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectuserbyemail(email);
        if (user == null) {
            writer.print("{\"msg\":\"该邮箱未曾注册过\"}");
            writer.flush();
            writer.close();
            return;
        }
        if (CheckEmailFormatUtil.isEmail(email)) {
            if (!email_verify.equalsIgnoreCase(captchaMapper.getcaptcha(email))) {
                writer.print("{\"msg\":\"邮箱验证码不正确\"}");
            } else {
                Integer uid = user.getUid();
                captchaMapper.updateuid(email, uid);
                upassword = PasswordsaltUtil.password(upassword, user);
                userMapper.updatepassword(uid, upassword);
                writer.print("{\"msg\":\"修改密码成功\"}");

            }
        } else {
            writer.print("{\"msg\":\"邮箱格式不正确\"}");
        }
        sqlSession.close();
        writer.flush();
        writer.close();
    }
}
