package tech.aaaaaa.util;

import org.apache.commons.codec.digest.DigestUtils;
import tech.aaaaaa.pojo.User;
//密码加盐
public class PasswordsaltUtil {
    public static String password(String password, User user){
        Integer uid = user.getUid();
        //密码一次加盐,MD5加密,第一次加uid
        String temp = password.substring(8,26)+uid;
        String password1salt = DigestUtils.md5Hex(temp);
        temp = password1salt.substring(5,25)+user.getRegistertime()+"saltString";
        //密码二次加盐,MD5加密,第二次加注册时间
        String password2salt = DigestUtils.md5Hex(temp);
        return password2salt;
    }
}
