package tech.aaaaaa.post;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.*;
import tech.aaaaaa.pojo.Posts;
import tech.aaaaaa.pojo.Reply;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "getReplyServlet", value = "/getReplyServlet")
public class getReplyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        Integer page = 1;
        page = Integer.valueOf(request.getParameter("page"));
        System.out.println(page);
        ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
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
            //默认不登录状态下权限为50
            limits = 50;
        } else {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectuser(Integer.parseInt(uid), verifycode);
            if (user != null) {
                UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
                limits = userGroupMapper.selectUserLimits(user.getUgid());
            }
        }
        PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
        Integer pid = Integer.valueOf(request.getParameter("pid"));
        Posts post=postMapper.selectPostContent(pid);
        PostClassMapper postClassMapper = sqlSession.getMapper(PostClassMapper.class);
        Integer pcid = post.getPcid();
        Reply emptyReply = new Reply();
        List<Reply> emptyReplyList = new ArrayList<>();
        if (limits >= postClassMapper.selectPostClassLimits(pcid)) {
            if (pcid != null) {
                System.out.println(4);
                Integer start = (page-1)*10;
                List<Reply> replyList = replyMapper.replyList(start,pid);
                String replyListString = JSON.toJSONString(replyList);
                System.out.println(replyListString);
                writer.print(replyListString);
            } else {
                emptyReply.setContent("主题不存在");
                emptyReplyList.add(emptyReply);
                String emptyReplyListString = JSON.toJSONString(emptyReplyList);
                writer.print(emptyReplyListString);
            }
        } else {
            emptyReply.setContent("访问权限不足");
            emptyReplyList.add(emptyReply);
            String emptyReplyListString = JSON.toJSONString(emptyReplyList);
            writer.print(emptyReplyListString);
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
