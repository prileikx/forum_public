package tech.aaaaaa.user;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.util.CheckCodeUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
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
        System.out.println(username);
        System.out.println(upassword);
        PrintWriter writer = response.getWriter();
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
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        this.doGet(request, response);
    }
}
