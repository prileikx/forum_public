package tech.aaaaaa.post;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.PostClassMapper;
import tech.aaaaaa.mapper.PostMapper;
import tech.aaaaaa.mapper.UserGroupMapper;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.CheckloginStatusUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
//发送帖子
@WebServlet(name = "sendpostServlet", value = "/sendpostServlet")
public class sendpostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        PostMapper postMapper=sqlSession.getMapper(PostMapper.class);
        Integer uid = CheckloginStatusUtil.CheckloginStatus(request);
        if (uid < 0)
        {
            writer.print("{\"msg\":\"登陆状态错误,请重新登录\"}");
            sqlSession.close();
            return;
        }
        String title = request.getParameter("title");
        Integer pcid = Integer.valueOf(request.getParameter("pcid"));
        String content = request.getParameter("content");
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectuserbyuid(uid);
        if (user == null){
            writer.print("{\"msg\":\"登录状态错误,请重新登录\"}");
        }
        else{
            PostClassMapper postClassMapper = sqlSession.getMapper(PostClassMapper.class);
            UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
            //检查用户权限是否大于等于版区权限
            if (userGroupMapper.selectUserLimits(user.getUgid())>=postClassMapper.selectPostClassLimits(pcid)){
                postMapper.insertpost(Integer.valueOf(uid),title,Integer.valueOf(pcid),content);
                userMapper.updatpostecount(Integer.valueOf(uid));
                writer.print("{\"msg\":\"发送成功\"}");
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
