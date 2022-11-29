package tech.aaaaaa.mapper;
import org.apache.ibatis.annotations.Param;

public interface CaptchaMapper {
    Integer checksendtime(@Param("email")String email);
    Integer insertsendemail(@Param("email")String email, @Param("captcha")String captcha);
    Integer updatesendemail(@Param("email")String email,@Param("captcha")String captcha);
    void updateuid(@Param("email")String email,@Param("uid")Integer uid);
    String getcaptcha(@Param("email")String email);
}
