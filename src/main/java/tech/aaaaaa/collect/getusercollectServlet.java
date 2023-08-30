package tech.aaaaaa.collect;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.*;
import tech.aaaaaa.pojo.Collect;
import tech.aaaaaa.pojo.User;
import tech.aaaaaa.util.SqlSessionFactoryUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
//获取用户收藏列表
@WebServlet(name = "getusercollectServlet", value = "/getusercollectServlet")
public class getusercollectServlet extends HttpServlet {
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
        List<Collect> emptycollectlist = new ArrayList<>();
        Collect emptycollect = new Collect();
        String emtpycollectjson = "";
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
            emptycollect.setTitle("登陆状态错误,请重新登录");
            emptycollectlist.add(emptycollect);
            emtpycollectjson = JSON.toJSONString(emptycollectlist);
            writer.print(emtpycollectjson);
            sqlSession.close();
            writer.flush();
            writer.close();
            return;
        }
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectuser(Integer.valueOf(uid),verifycode);
        if (user == null){
            emptycollect.setTitle("登陆状态错误,请重新登录");
            emptycollectlist.add(emptycollect);
            emtpycollectjson = JSON.toJSONString(emptycollectlist);
            writer.print(emtpycollectjson);
        }
        else{
            CollectMapper collectMapper = sqlSession.getMapper(CollectMapper.class);
            Integer page = Integer.valueOf(request.getParameter("page"));
            if (page == null){
                page=1;
            }
            List<Collect> collects = collectMapper.selectrusercollect(Integer.valueOf(uid),(page-1)*10);
            if(collects==null||collects.isEmpty()){
                emptycollect.setTitle("您未收藏任何帖子哦~");
                emptycollectlist.add(emptycollect);
                emtpycollectjson = JSON.toJSONString(emptycollectlist);
                writer.print(emtpycollectjson);
            }else {
            String collectjson = JSON.toJSONString(collects);
            writer.print(collectjson);
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
