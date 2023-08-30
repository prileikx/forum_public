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
//删除回复
@WebServlet(name = "deletereplyServlet", value = "/deletereplyServlet")
public class deletereplyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        Integer uid = CheckloginStatusUtil.CheckloginStatus(request);
        if(uid < 0)
        {
            writer.print("{\"msg\":\"登录状态错误,请重新登录\"}");
            sqlSession.close();
            return;
        }
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectuserbyuid(uid);
        if (user == null){
            writer.print("{\"msg\":\"登陆状态错误,请重新登录\"}");
        }
        else{
            UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
            ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
            Integer rid = Integer.valueOf(request.getParameter("rid"));
            Reply reply = replyMapper.selectreplybyrid(rid);
            if (userGroupMapper.selectUserLimits(user.getUgid())>=200 || user.getUid()==reply.getUid()){
                replyMapper.deleteReplybyRid(rid);
                writer.print("{\"msg\":\"删除成功\"}");
            }
            else {
                writer.print("{\"msg\":\"用户权限不足\"}");
            }
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
