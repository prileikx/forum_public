package tech.aaaaaa.post;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.PostClassMapper;
import tech.aaaaaa.mapper.PostMapper;
import tech.aaaaaa.mapper.UserGroupMapper;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.Posts;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "searchpostcountServlet", value = "/searchpostcountServlet")
public class searchpostcountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        String URI = request.getRequestURI();
        String pcenglishname = URI.substring(13);
        PostClassMapper postClassMapper = sqlSession.getMapper(PostClassMapper.class);
        Integer pcid = postClassMapper.selectpcid(pcenglishname);
        List<Posts> emptyPostList = new ArrayList<>();
        Posts emptyPost = new Posts();
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
        Integer limits = 0;
        if (uid == null || verifycode == null) {
            limits = 40;
        } else {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectuser(Integer.parseInt(uid), verifycode);
            if (user != null) {
                UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
                limits = userGroupMapper.selectUserLimits(user.getUgid());
            }
        }
        if (limits >= 50) {
            PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
            String searchcontent=request.getParameter("searchcontent");
            System.out.println(searchcontent);
            Integer totalcount = postMapper.searchpostcountbycontent(searchcontent);
            System.out.println(totalcount);
            if (totalcount==0) {
                writer.print("{\"totalcount\":\""+1+"\"}");
            } else {
                writer.print("{\"totalcount\":\""+totalcount+"\"}");
            }
        } else {
            writer.print("{\"totalcount\":\""+1+"\"}");
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
