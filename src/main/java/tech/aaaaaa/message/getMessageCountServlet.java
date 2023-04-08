package tech.aaaaaa.message;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.MessageMapper;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.Message;
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
//获得用户消息计数
@WebServlet(name = "getMessageCountServlet", value = "/getMessageCountServlet")
public class getMessageCountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        Integer uid = CheckloginStatusUtil.CheckloginStatus(request);
        if(uid < 0){
            writer.print("{\"msgtotalcount\":\"" + 1 + "\"}");
            sqlSession.close();
            return;
        }
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectuserbyuid(uid);
        if (user == null) {
            writer.print("{\"msgtotalcount\":\"" + 1 + "\"}");
        } else {
            MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
            int msgtotalcount = messageMapper.getMessageCount(Integer.valueOf(uid));
            if (msgtotalcount != 0) {
                writer.print("{\"msgtotalcount\":\"" + msgtotalcount + "\"}");
            } else {
                writer.print("{\"msgtotalcount\":\"" + 1 + "\"}");
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
