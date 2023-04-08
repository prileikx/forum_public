package tech.aaaaaa.mapper;
import org.apache.ibatis.annotations.Param;

public interface CaptchaMapper {
    //检查发送时间,60秒内不得再次发送邮件
    Integer checksendtime(@Param("email")String email);
    //添加邮箱和对应的验证码
    Integer insertsendemail(@Param("email")String email, @Param("captcha")String captcha);
    //更新验证码(用户再次获取验证码的时候)
    Integer updatesendemail(@Param("email")String email,@Param("captcha")String captcha);
    //更新captcha表的uid(没用到这个)
    void updateuid(@Param("email")String email,@Param("uid")Integer uid);
    //获得验证码
    String getcaptcha(@Param("email")String email);
}
