package tech.aaaaaa;

import tech.aaaaaa.util.CheckCodeUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HelloMaven {
    public static void main(String[] args) throws IOException {

//    String currentPath=System.getProperty("user.dir");
//System.out.println(currentPath);
        OutputStream fos = new FileOutputStream("d://study//javaweb课程//a.jpg");
        String code=CheckCodeUtil.outputVerifyImage(100,50,fos,4);
        System.out.println(code);
    }
}
