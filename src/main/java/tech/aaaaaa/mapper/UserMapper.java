package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;
import tech.aaaaaa.pojo.User;

import java.util.List;

public interface UserMapper {
    Integer checkusernameifcanbeuse(@Param("username")String username);
    Integer checkUserEmailIfWasRegister(@Param("email")String email);
    Integer adduser(@Param("username")String username,@Param("upassword")String upassword,@Param("email")String email);
    Integer checkemailifwasregister(@Param("email")String email);
    Integer login(@Param("username")String username,@Param("upassword")String upassword);
    void addloginverify(@Param("uid")Integer uid,@Param("verify")String verify);
}
