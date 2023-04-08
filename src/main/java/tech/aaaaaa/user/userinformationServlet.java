package tech.aaaaaa.user;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.PostClassMapper;
import tech.aaaaaa.mapper.UserGroupMapper;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.Posts;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.CheckloginStatusUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
//用户的所有信息(不包括敏感信息)
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
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        Integer uid = CheckloginStatusUtil.CheckloginStatus(request);
        if (uid < 0) {
            User user = userMapper.selectuserbyuid(Integer.valueOf(reuid));
            user.setEmail("not show");
            user.setVerify("not show");
            user.setUpassword("not show");
            String userjson = JSON.toJSONString(user);
            writer.print(userjson);
        } else {
            User user = userMapper.selectuserbyuid(Integer.valueOf(reuid));
            if (uid != Integer.valueOf(reuid)){
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
