package tech.aaaaaa.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.UserMapper;
import tech.aaaaaa.pojo.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CheckloginStatusUtil {
    public static Integer CheckloginStatus(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Integer uid = null;
        String verifycode = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals("uid")) {
                    uid = Integer.valueOf(cookie.getValue());
                } else if (name.equals("verifycode")) {
                    verifycode = cookie.getValue();
                }
            }
        }else {
            //用户cookie为空(用户首次访问该网站)
            return -3;
        }
        if (uid==null || verifycode == null){
            //用户未登录,cookie中未保存登录信息
            return -1;
        }
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectuser(Integer.valueOf(uid), verifycode);
        sqlSession.close();
        if (user == null) {
            //无法查询到用户
            return -2;
        } else {
            //成功查询到用户,返回用户uid
            return uid;
        }
    }
}
