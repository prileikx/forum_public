package tech.aaaaaa.util;
//检查字符串是否只包含数字和字母
public class IsLetterDigit {
    public static boolean stringchecknumal(String str){
        String regex="^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }
}
