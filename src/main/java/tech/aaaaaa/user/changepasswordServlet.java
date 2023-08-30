package tech.aaaaaa.user;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.CheckloginStatusUtil;
import tech.aaaaaa.util.PasswordsaltUtil;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
//登陆后修改用户密码
@WebServlet(name = "changepasswordServlet", value = "/changepasswordServlet")
public class changepasswordServlet extends HttpServlet {
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
                if(user.getUpassword().equals(password)){
                    String afpassword = request.getParameter("afpassword");
                    afpassword = PasswordsaltUtil.password(afpassword,user);
                        userMapper.updatepassword(Integer.valueOf(uid),afpassword);
                        userMapper.updateverify(Integer.valueOf(uid));
                        writer.print("{\"msg\":\"修改成功,请重新登录\"}");
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
