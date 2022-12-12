package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;
import tech.aaaaaa.pojo.User;

import javax.swing.*;
import java.util.List;

public interface UserMapper {
    Integer checkusernameifcanbeuse(@Param("username")String username);
    Integer checkUserEmailIfWasRegister(@Param("email")String email);
    Integer adduser(@Param("username")String username,@Param("upassword")String upassword,@Param("email")String email);
    Integer checkemailifwasregister(@Param("email")String email);
    Integer login(@Param("username")String username,@Param("upassword")String upassword);
    void addloginverify(@Param("uid")Integer uid,@Param("verify")String verify);
    //查询用户
    User selectuser(@Param("uid")Integer uid,@Param("verify")String verify);
    //查询ugid
    Integer selectugid(@Param("reuid")Integer reuid);
    //上传头像
    Integer uploadimg(@Param("img")String img,@Param("uid")Integer uid);
    //凭借uid查询所有信息
    User selectuserbyuid(@Param("uid")Integer uid);
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
    //修改密码后删除verify
    void updateverify(@Param("uid")Integer uid);
}
