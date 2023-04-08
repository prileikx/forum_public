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
//获取帖子内容
@WebServlet(name = "getPostContentServlet", value = "/getPostContentServlet/*")
public class getPostContentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        Integer page = 1;
        page = Integer.valueOf(request.getParameter("page"));
        String URI = request.getRequestURI();
        String pid = URI.substring(23);
        PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
        Posts post = postMapper.selectPostContent(Integer.valueOf(pid));
        Posts emptyPost = new Posts();
        Integer uid = CheckloginStatusUtil.CheckloginStatus(request);
        Integer limits = 0;
        if (uid < 0) {
            //默认不登录状态下权限为50
            limits = 50;
        } else {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectuserbyuid(uid);
            if (user != null) {
                UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
                limits = userGroupMapper.selectUserLimits(user.getUgid());
            }
        }
        PostClassMapper postClassMapper = sqlSession.getMapper(PostClassMapper.class);
        Integer pcid = post.getPcid();
        String poststring = JSON.toJSONString(post);
        if (limits >= postClassMapper.selectPostClassLimits(pcid)) {
            if (pcid != null) {
                String postContent = JSON.toJSONString(post);
                postMapper.updateviewcount(Integer.valueOf(pid));
                writer.print(postContent);
            } else {
                emptyPost.setTitle("主题不存在");
                String emptyPostListString = JSON.toJSONString(emptyPost);
                writer.print(emptyPostListString);
            }
        } else {
            emptyPost.setTitle("访问权限不足");
            String emptyPostString = JSON.toJSONString(emptyPost);
            writer.print(emptyPostString);
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
