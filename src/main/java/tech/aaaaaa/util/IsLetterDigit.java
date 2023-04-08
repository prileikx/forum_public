package tech.aaaaaa.util;
//检查字符串是否只包含数字和字母和中文
public class IsLetterDigit {
    public static boolean stringchecknumal(String str){
        String regex="^[a-z0-9A-Z\\u4e00-\\u9fa5]+$";
        return str.matches(regex);
    }
}
