package tech.aaaaaa.user;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.PostClassMapper;
import tech.aaaaaa.mapper.UserGroupMapper;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.Posts;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "/userinformationServlet", value = "/userinformationServlet")
public class userinformationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        String URI = request.getRequestURI();
        String reuid = request.getParameter("reuid");
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
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        if (uid == null || verifycode == null) {
            User user = userMapper.selectuserbyuid(Integer.valueOf(reuid));
            user.setEmail("not show");
            user.setVerify("not show");
            user.setUpassword("not show");
            String userjson = JSON.toJSONString(user);
            writer.print(userjson);
        } else {
            User user = userMapper.selectuser(Integer.parseInt(reuid), verifycode);
            if (user == null){
                user = userMapper.selectuserbyuid(Integer.valueOf(reuid));
                user.setEmail("not show");
            }
            user.setVerify("not show");
            user.setUpassword("not show");
            String userjson = JSON.toJSONString(user);
            writer.print(userjson);
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
