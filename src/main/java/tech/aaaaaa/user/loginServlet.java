package tech.aaaaaa.user;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.CheckCodeUtil;
import tech.aaaaaa.util.PasswordsaltUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
//登录
@WebServlet(name = "loginServlet", value = "/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        String username = request.getParameter("username");
        String upassword = request.getParameter("upassword");
        PrintWriter writer = response.getWriter();
        User user = userMapper.selectuserbyusername(username);
        if (user==null){
            user = userMapper.selectuserbyemail(username);
        }
        if (user==null){
            writer.print("{\"msg\":\"用户名或密码错误\"}");
            sqlSession.close();
            writer.flush();
            writer.close();
            return;
        }
        username=user.getUsername();
        upassword = PasswordsaltUtil.password(upassword,user);
        Integer uid = userMapper.login(username,upassword);
        if (uid!=null){
            Cookie uidcookie = new Cookie("uid",Integer.toString(uid));
            String loginverifycode = CheckCodeUtil.generateVerifyCode(40);
            Cookie loginverifycodecookie = new Cookie("verifycode",loginverifycode);
            String ifsave = request.getParameter("ifsave");
            if (ifsave.equals("1"))
            {
                uidcookie.setMaxAge(60*60*24*30);
                loginverifycodecookie.setMaxAge(60*60*24*30);
            }
            response.addCookie(uidcookie);
            response.addCookie(loginverifycodecookie);
            userMapper.addloginverify(uid,loginverifycode);
            writer.print("{\"msg\":\"登录成功\"}");
        }else {
            writer.print("{\"msg\":\"用户名或密码错误\"}");
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
