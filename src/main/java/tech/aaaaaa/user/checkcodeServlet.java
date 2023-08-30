package tech.aaaaaa.user;

import tech.aaaaaa.util.CheckCodeUtil;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;
/*
生成验证码
 */
@WebServlet(name = "checkcodeServlet", value = "/checkcodeServlet")
public class checkcodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json;charset=utf-8");
        HttpSession session = request.getSession();
        ServletOutputStream servletOutputStream = response.getOutputStream();
        String checkCode = CheckCodeUtil.outputVerifyImage(100,50,servletOutputStream,4);
        session.setAttribute("checkcodegen",checkCode);
        response.getOutputStream().flush();
        response.getOutputStream().close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        this.doGet(request, response);
    }
}
