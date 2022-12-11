package tech.aaaaaa.user;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.ReplyMapper;
import tech.aaaaaa.mapper.UserGroupMapper;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "changeemailServlet", value = "/changeemailServlet")
public class changeemailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        Cookie[] cookies = request.getCookies();
        String uid = null;
        String verifycode = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals("uid")) {
                    uid = cookie.getValue();
                } else if (name.equals("verifycode")) {
                    verifycode = cookie.getValue();
                }
            }
        }
        if (uid == null || verifycode == null) {
            writer.print("{\"msg\":\"用户登录状态验证错误,请重新登录\"}");
        } else {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectuser(Integer.parseInt(uid), verifycode);
            if (user != null) {
                String password = request.getParameter("password");
                System.out.println(password);
                System.out.println(user.getUpassword());
                System.out.println(user.getUpassword().equals(password));
                if(user.getUpassword().equals(password)) {
                    String email = request.getParameter("email");
                    userMapper.updateemail(Integer.valueOf(uid), email);
                    writer.print("{\"msg\":\"修改成功\"}");
                }else {
                    writer.print("{\"msg\":\"原密码输入错误,请重新输入\"}");
                }
            }
            else {
                writer.print("{\"msg\":\"用户登录状态验证错误,请重新登录\"}");
            }
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
