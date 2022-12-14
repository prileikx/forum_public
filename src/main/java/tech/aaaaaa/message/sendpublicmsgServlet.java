package tech.aaaaaa.message;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.*;
import tech.aaaaaa.pojo.Posts;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "sendpublicmsgServlet", value = "/sendpublicmsgServlet")
public class sendpublicmsgServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        PostMapper postMapper=sqlSession.getMapper(PostMapper.class);
        Cookie[] cookies = request.getCookies();
        String uid = null;
        String verifycode = null;
        if(cookies!=null){
            for (Cookie cookie:cookies){
                String name = cookie.getName();
                if (name.equals("uid")){
                    uid = cookie.getValue();
                } else if (name.equals("verifycode")) {
                    verifycode = cookie.getValue();
                }
            }
        }else
        {
            writer.print("{\"msg\":\"登录状态错误,请重新登录\"}");
            sqlSession.close();
            return;
        }
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectuser(Integer.valueOf(uid),verifycode);
        if (user == null){
            writer.print("{\"msg\":\"登陆状态错误,请重新登录\"}");
        }
        else{
            UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
            if (userGroupMapper.selectUserLimits(user.getUgid())>=200){
                MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
                messageMapper.insertpublicmsg(request.getParameter("publicmsg_content"));
                writer.print("{\"msg\":\"发布成功\"}");
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
