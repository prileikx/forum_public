package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;
import tech.aaaaaa.pojo.User;

import javax.swing.*;
import java.util.List;

public interface UserMapper {
    //检查用户名是否可以被使用
    Integer checkusernameifcanbeuse(@Param("username")String username);
    //检查邮箱是否被注册
    Integer checkUserEmailIfWasRegister(@Param("email")String email);
    //添加用户
    Integer adduser(@Param("username")String username,@Param("upassword")String upassword,@Param("email")String email);
    //检查邮箱是否被注册
    Integer checkemailifwasregister(@Param("email")String email);
    //登录
    Integer login(@Param("username")String username,@Param("upassword")String upassword);
    //添加用户验证信息cookie
    void addloginverify(@Param("uid")Integer uid,@Param("verify")String verify);
    //查询用户
    User selectuser(@Param("uid")Integer uid,@Param("verify")String verify);
    //查询ugid
    Integer selectugid(@Param("reuid")Integer reuid);
    //上传头像
    Integer uploadimg(@Param("img")String img,@Param("uid")Integer uid);
    //凭借uid查询所有信息
    User selectuserbyuid(@Param("uid")Integer uid);
    //凭借email查询用户
    User selectuserbyemail(@Param("email")String email);
    //添加发帖数计数
    void updatpostecount(@Param("uid")Integer uid);
    //添加回复计数
    void updatereplycount(@Param("uid")Integer uid);
    //修改邮箱
    void updateemail(@Param("uid")Integer uid,@Param("email")String email);
    //修改密码
    void updatepassword(@Param("uid")Integer uid,@Param("password")String password);
    //修改用户名
    void updateusername(@Param("uid")Integer uid,@Param("username")String username);
    //修改简介
    void updatedescribe(@Param("uid")Integer uid,@Param("describe")String describe);
    //(修改密码后)删除verify
    void updateverify(@Param("uid")Integer uid);
    //凭借用户名获取uid
    User selectuserbyusername(@Param("username")String username);
}
