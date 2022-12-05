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
//发送注册邮件
@WebServlet(name = "sendregisteremailServlet", value = "/sendregisteremailServlet")
public class sendregisteremailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        request.getParameter("email");
        SqlSessionFactory sqlSessionFactory= SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        UserMapper User = sqlSession.getMapper(UserMapper.class);
        CaptchaMapper captchaMapper = sqlSession.getMapper(CaptchaMapper.class);
        String email = request.getParameter("email");
        PrintWriter writer = response.getWriter();
        if(CheckEmailFormatUtil.isEmail(email)){
            if(User.checkUserEmailIfWasRegister(email)==0){
                Integer checksendtime = captchaMapper.checksendtime(email);
                if (checksendtime==null)//检查距离上次发送验证码的时间,返回为null说明该邮箱从未发送过验证码,返回数字即为距离上次发送的时间间隔(秒数),300即为5分钟
                {
                    String verifycode = CheckCodeUtil.generateVerifyCode(4);
                    captchaMapper.insertsendemail(email,verifycode);
                    writer.print("{\"msg\":\"验证码发送成功\"}");
                }
                else{
                    if (checksendtime>=300){
                        String verifycode = CheckCodeUtil.generateVerifyCode(4);
                        captchaMapper.updatesendemail(email,verifycode);
                        writer.print("{\"msg\":\"验证码发送成功\"}");
                    }
                    else {
                        writer.print("{\"msg\":\"距离上次发送时间过短,请隔段时间后重试\"}");
                    }
                }

            } else {
                    writer.print("{\"msg\":\"邮箱已被注册\"}");
            }
        }
        else {
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
