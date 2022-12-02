package tech.aaaaaa.user;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.util.IsLetterDigit;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "checkusernameifcanbeuseServlet", value = "/checkusernameifcanbeuseServlet")
public class checkusernameifcanbeuseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory= SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        String username = request.getParameter("username");
        Integer result = userMapper.checkusernameifcanbeuse(username);
        PrintWriter writer = response.getWriter();
        if (result>0&&username!=""&&username!=null){
            writer.print("{\"msg\":\"用户名已被使用,请更换一个\"}");
        } else if (username.length()<=2) {
            writer.print("{\"msg\":\"用户名过短,请更换一个\"}");
        } else if (!IsLetterDigit.stringchecknumal(username)) {
            writer.print("{\"msg\":\"用户名只能由数字和字母或汉字构成\"}");
        } else {
            writer.print("{\"msg\":\"该用户名可以使用\"}");
        }
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        this.doGet(request, response);
    }
}
