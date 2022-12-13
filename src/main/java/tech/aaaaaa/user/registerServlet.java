package tech.aaaaaa.user;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tech.aaaaaa.mapper.CaptchaMapper;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.util.CheckCodeUtil;
import tech.aaaaaa.util.CheckEmailFormatUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
//注册用户
@WebServlet(name = "registerServlet", value = "/registerServlet")
public class registerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        //获取验证码的值
        String verify_code = request.getParameter("verify_code");
        HttpSession session = request.getSession();
        String checkcode = (String)session.getAttribute("checkcodegen");
        PrintWriter writer= response.getWriter();
        if (checkcode==null){
            writer.print("{\"msg\":\"我们无法比对此验证码,请点击'看不清?换一张'重新获取验证码输入\"}");
            return;
        }
        //比对验证码
        if(!checkcode.equalsIgnoreCase(verify_code)){
            writer.print("{\"msg\":\"验证码不正确\"}");
            return;
        }
        System.out.println(2);
        String username = request.getParameter("username");
        if (username == null){
            writer.print("{\"msg\":\"用户名不能为空\"}");
            return;
        }
        System.out.println(3);
        String email = request.getParameter("email");
        if (email == null){
            writer.print("{\"msg\":\"邮箱不能为空\"}");
            return;
        }
        System.out.println(4);
        String email_verify = request.getParameter("email_verify");
        if (email_verify == null){
            writer.print("{\"msg\":\"邮箱验证码不能为空\"}");
            return;
        }
        System.out.println(5);
        String upassword = request.getParameter("upassword");
        if (upassword== null){
            writer.print("{\"msg\":\"密码不能为空\"}");
            return;
        }
        SqlSessionFactory sqlSessionFactory= SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        CaptchaMapper captchaMapper=sqlSession.getMapper(CaptchaMapper.class);
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
            if (CheckEmailFormatUtil.isEmail(email)) {
                if (userMapper.checkemailifwasregister(email)==0){
                if (!email_verify.equalsIgnoreCase(captchaMapper.getcaptcha(email))) {
                    writer.print("{\"msg\":\"邮箱验证码不正确\"}");
                } else {
                    if (userMapper.checkusernameifcanbeuse(request.getParameter("username")) == 0 && username.length() > 2) {
                        Integer uid = userMapper.adduser(username, upassword, email);
                        captchaMapper.updateuid(email, uid);
                        writer.print("{\"msg\":\"注册成功\"}");
                    } else {
                        writer.print("{\"msg\":\"该用户名已被注册,请更换一个\"}");
                    }
                }
            }else {
                    writer.print("{\"msg\":\"该邮箱已被注册,请更换一个,如忘记密码请使用找回密码功能\"}");
                }
            }
            else {
                writer.print("{\"msg\":\"邮箱格式不正确\"}");
            }
        sqlSession.close();
        writer.flush();
        writer.close();
        }
}
