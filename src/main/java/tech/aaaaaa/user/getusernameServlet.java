package tech.aaaaaa.user;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getusernameServlet", value = "/getusernameServlet")
public class getusernameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        PrintWriter writer = response.getWriter();
        Cookie[] cookies = request.getCookies();
        String uid = null;
        String verifycode = null;
        if(cookies!=null){
            for (Cookie cookie:cookies){
                String name = cookie.getName();
                if (name.equals("uid")){
                    uid = cookie.getValue();
                } else if (name.equals("verifycode")) {
                    verifycode = cookie.getValue();
                }
            }
        }
        if(uid == null ||verifycode == null){
            writer.print("{\"userstatus\":\"400\"}");
        }else {
            User user = userMapper.selectuser(Integer.parseInt(uid),verifycode);
            String username = user.getUsername();
            writer.print("{\"userstatus\":200,\"username\":\""+username+"\",\"uid\":\""+uid+"\"}");
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
