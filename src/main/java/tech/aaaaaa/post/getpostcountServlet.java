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
//获取某版区下帖子总数量
@WebServlet(name = "getpostcountServlet", value = "/getpostcountServlet")
public class getpostcountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        PostClassMapper postClassMapper = sqlSession.getMapper(PostClassMapper.class);
        String postclass = request.getParameter("postclass");
        Integer pcid = postClassMapper.selectpcid(postclass);
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
                Integer totalcount = postMapper.selectcountPost(pcid);
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
