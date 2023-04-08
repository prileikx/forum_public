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
import tech.aaaaaa.util.CheckloginStatusUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
//搜索帖子
@WebServlet(name = "searchpostServlet", value = "/searchpostServlet")
public class searchpostServlet extends HttpServlet {
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
        Integer uid = CheckloginStatusUtil.CheckloginStatus(request);
        Integer limits = 0;
        if (uid < 0) {
            limits = 40;
        } else {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectuserbyuid(uid);
            if (user != null) {
                UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
                limits = userGroupMapper.selectUserLimits(user.getUgid());
            }
        }
        if (limits >= 50) {
            PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
            Integer page = Integer.valueOf(request.getParameter("page"));
            if (page == null) {
                page = 1;
            }
            Integer start = (page - 1) * 10;
            String searchcontent = request.getParameter("searchcontent");
            List<Posts> posts = postMapper.searchpostbycontent(searchcontent, start);
            if (posts != null && !posts.isEmpty()) {
                String postList = JSON.toJSONString(posts);
                writer.print(postList);
            } else {
                emptyPost.setTitle("没有搜索到任何结果");
                emptyPost.setPcid(pcid);
                emptyPostList.add(emptyPost);
                String emptyPostListString = JSON.toJSONString(emptyPostList);
                writer.print(emptyPostListString);
            }
        } else {
            emptyPost.setTitle("权限不足,仅登录用户可使用搜索功能");
            emptyPost.setPcid(pcid);
            emptyPostList.add(emptyPost);
            String emptyPostListString = JSON.toJSONString(emptyPostList);
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
