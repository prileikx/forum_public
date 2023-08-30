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

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
//获取某版区下帖子列表
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
        Integer uid = CheckloginStatusUtil.CheckloginStatus(request);
        Integer limits = 0;
        if (uid < 0) {
            limits = 50;
        } else {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectuserbyuid(uid);
            if (user != null) {
                UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
                limits = userGroupMapper.selectUserLimits(user.getUgid());
            }
        }
        if (limits>=postClassMapper.selectPostClassLimits(pcid)){
        if (pcid != null) {
            PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
            Integer page = Integer.valueOf(request.getParameter("page"));
            if (page==null){
                page=1;
            }
            Integer start = (page-1)*10;
            List<Posts> posts = postMapper.selectpostList(pcid,start);
            if (posts != null && !posts.isEmpty()) {
                String postList = JSON.toJSONString(posts);
                writer.print(postList);
            } else {
                emptyPost.setTitle("版区没有任何帖子");
                emptyPost.setPcid(pcid);
                emptyPostList.add(emptyPost);
                String emptyPostListString = JSON.toJSONString(emptyPostList);
                writer.print(emptyPostListString);
            }
        } else {
            emptyPost.setTitle("不存在的版区");
            emptyPost.setPcid(pcid);
            emptyPostList.add(emptyPost);
            String emptyPostListString = JSON.toJSONString(emptyPostList);
            writer.print(emptyPostListString);
        }
        }else {
            emptyPost.setTitle("访问权限不足");
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
