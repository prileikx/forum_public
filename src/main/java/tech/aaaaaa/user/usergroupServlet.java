package tech.aaaaaa.user;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.UserGroupMapper;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.UserGroup;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
//返回用户组
@WebServlet(name = "usergroupServlet", value = "/usergroupServlet")
public class usergroupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        String reuid = request.getParameter("reuid");
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        Integer ugid = userMapper.selectugid(Integer.valueOf(reuid));
        UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
        UserGroup userGroup = userGroupMapper.selectUserGroup(ugid);
        String userjson = JSON.toJSONString(userGroup);
        writer.print(userjson);
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
