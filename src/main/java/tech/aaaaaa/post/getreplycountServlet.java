package tech.aaaaaa.post;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.*;
import tech.aaaaaa.pojo.Reply;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.CheckloginStatusUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
//获取某帖子回复总数量
@WebServlet(name = "getreplycountServlet", value = "/getreplycountServlet")
public class getreplycountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        PostClassMapper postClassMapper = sqlSession.getMapper(PostClassMapper.class);
        Integer pid = Integer.valueOf(request.getParameter("pid"));
        PostMapper postMapper=sqlSession.getMapper(PostMapper.class);
        Integer pcid = postMapper.selectpcid(pid);
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
                ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
                Integer totalcount = replyMapper.selectreplycount(pid);
                if (totalcount != null) {
                    if (totalcount==0){
                        totalcount=1;
                    }
                    writer.print("{\"totalcount\":\""+totalcount+"\"}");
                } else {
                    writer.print("{\"totalcount\":\""+1+"\"}");
                }
            } else {
                writer.print("{\"totalcount\":\""+1+"\"}");
            }
        }else {
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
