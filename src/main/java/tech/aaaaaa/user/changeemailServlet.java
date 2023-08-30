package tech.aaaaaa.user;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.ReplyMapper;
import tech.aaaaaa.mapper.UserGroupMapper;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.CheckEmailFormatUtil;
import tech.aaaaaa.util.CheckloginStatusUtil;
import tech.aaaaaa.util.PasswordsaltUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
//修改用户邮箱
@WebServlet(name = "changeemailServlet", value = "/changeemailServlet")
public class changeemailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        PrintWriter writer = response.getWriter();
        Integer uid = CheckloginStatusUtil.CheckloginStatus(request);
        if (uid < 0) {
            writer.print("{\"msg\":\"用户登录状态验证错误,请重新登录\"}");
        } else {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectuserbyuid(uid);
            if (user != null) {
                String password = request.getParameter("password");
                password = PasswordsaltUtil.password(password,user);
                if(user.getUpassword().equals(password)) {
                    String email = request.getParameter("email");
                    if(CheckEmailFormatUtil.isEmail(email)){
                        userMapper.updateemail(Integer.valueOf(uid), email);
                        writer.print("{\"msg\":\"修改成功\"}");
                    }else {
                        writer.print("{\"msg\":\"邮箱格式错误,请重新输入\"}");
                    }
                }else {
                    writer.print("{\"msg\":\"原密码输入错误,请重新输入\"}");
                }
            }
            else {
                writer.print("{\"msg\":\"用户登录状态验证错误,请重新登录\"}");
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
