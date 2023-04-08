package tech.aaaaaa.collect;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.*;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.CheckEmailFormatUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
//添加用户收藏
@WebServlet(name = "insertcollectServlet", value = "/insertcollectServlet")
public class insertcollectServlet extends HttpServlet {
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
            writer.print("{\"msg\":\"登陆状态错误,请重新登录\"}");
            sqlSession.close();
            return;
        }
        Integer pid = Integer.valueOf(request.getParameter("pid"));
        Integer pcid = postMapper.selectpcid(pid);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectuser(Integer.valueOf(uid),verifycode);
        if (user == null){
            writer.print("{\"msg\":\"登录状态错误,请重新登录\"}");
        }
        else{
            PostClassMapper postClassMapper = sqlSession.getMapper(PostClassMapper.class);
            UserGroupMapper userGroupMapper = sqlSession.getMapper(UserGroupMapper.class);
            //检查用户权限是否大于等于版区权限
            if (userGroupMapper.selectUserLimits(user.getUgid())>=postClassMapper.selectPostClassLimits(pcid)){
                CollectMapper collectMapper = sqlSession.getMapper(CollectMapper.class);
                if(collectMapper.selectifcollect(Integer.valueOf(uid),pid)==null){
                    collectMapper.insertcollect(Integer.valueOf(uid),pid);
                    writer.print("{\"msg\":\"收藏成功\"}");
                }else {
                    writer.print("{\"msg\":\"已经收藏过了,是否取消收藏?\"}");
                }
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
