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

@WebServlet("/getPostList/*")
public class getPostList extends HttpServlet {
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
            limits = 50;
        } else {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectuser(Integer.parseInt(uid), verifycode);
            if (user != null) {
                UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
                limits = userGroupMapper.selectUserLimits(user.getUgid());
            }
        }
        if (limits>=postClassMapper.selectPostClassLimits(pcid)){
        if (pcid != null) {
            PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
            List<Posts> posts = postMapper.selectpostList(pcid);
            if (posts != null && !posts.isEmpty()) {
                String postList = JSON.toJSONString(posts);
                writer.print(postList);
            } else {
                emptyPost.setTitle("版区没有任何帖子");
                emptyPostList.add(emptyPost);
                String emptyPostListString = JSON.toJSONString(emptyPostList);
                writer.print(emptyPostListString);
            }
        } else {
            emptyPost.setTitle("不存在的版区");
            emptyPostList.add(emptyPost);
            String emptyPostListString = JSON.toJSONString(emptyPostList);
            System.out.println(emptyPostListString);
            writer.print(emptyPostListString);
        }
        }else {
            emptyPost.setTitle("访问权限不足");
            emptyPostList.add(emptyPost);
            String emptyPostListString = JSON.toJSONString(emptyPostList);
            System.out.println(emptyPostListString);
            writer.print(emptyPostListString);
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
