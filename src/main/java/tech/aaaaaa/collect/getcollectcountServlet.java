package tech.aaaaaa.collect;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.CollectMapper;
import tech.aaaaaa.mapper.PostMapper;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.Collect;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
//获取用户收藏总数量
@WebServlet(name = "getcollectcountServlet", value = "/getcollectcountServlet")
public class getcollectcountServlet extends HttpServlet {
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
            writer.print("{\"totalcount\":\""+1+"\"}");
            sqlSession.close();
            writer.flush();
            writer.close();
            return;
        }
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectuser(Integer.valueOf(uid),verifycode);
        if (user == null){
            writer.print("{\"totalcount\":\""+1+"\"}");
        }
        else{
            CollectMapper collectMapper = sqlSession.getMapper(CollectMapper.class);
            Integer totalcount = collectMapper.selectcollectcount(Integer.valueOf(uid));
            writer.print("{\"totalcount\":\""+totalcount+"\"}");
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
