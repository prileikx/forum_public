package tech.aaaaaa.message;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.*;
import tech.aaaaaa.pojo.Message;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "getMessageListServlet", value = "/getMessageListServlet")
public class getMessageListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        Cookie[] cookies = request.getCookies();
        String uid = null;
        String verifycode = null;
        List<Message> empryMessageList = new ArrayList<>();
        Message emptyMessage = new Message();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals("uid")) {
                    uid = cookie.getValue();
                } else if (name.equals("verifycode")) {
                    verifycode = cookie.getValue();
                }
            }
        } else {
            emptyMessage.setMsg("登陆状态错误,请重新登录");
            empryMessageList.add(emptyMessage);
            String emptymessageListjson = JSON.toJSONString(empryMessageList);
            writer.print(emptymessageListjson);
            sqlSession.close();
            return;
        }
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectuser(Integer.valueOf(uid), verifycode);
        if (user == null) {
            emptyMessage.setMsg("登陆状态错误,请重新登录");
            empryMessageList.add(emptyMessage);
            String emptymessageListjson = JSON.toJSONString(empryMessageList);
            writer.print(emptymessageListjson);
        } else {
            MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
            int page = Integer.parseInt(request.getParameter("page"));
            if (page <= 0) {
                page = 1;
            }
            List<Message> messageList = messageMapper.getMessageList(Integer.valueOf(uid), (page - 1) * 10);
            if (messageList != null && !messageList.isEmpty()) {
                String messageListjson = JSON.toJSONString(messageList);
                writer.print(messageListjson);
            } else {
                emptyMessage.setMsg("没有任何消息哦~");
                empryMessageList.add(emptyMessage);
                String emptymessageListjson = JSON.toJSONString(empryMessageList);
                writer.print(emptymessageListjson);
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
