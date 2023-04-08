package tech.aaaaaa.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tech.aaaaaa.mapper.CaptchaMapper;
import tech.aaaaaa.mapper.UserMapper;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;
//发送邮件
public class SendMailUtil {
    public static Integer SendMailUtilWithVerifycode(String email,String EmailTitle,String context) throws MessagingException, GeneralSecurityException {
        SqlSessionFactory sqlSessionFactory= SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        UserMapper User = sqlSession.getMapper(UserMapper.class);
        CaptchaMapper captchaMapper = sqlSession.getMapper(CaptchaMapper.class);
        if(CheckEmailFormatUtil.isEmail(email)){

                Integer checksendtime = captchaMapper.checksendtime(email);
                if (checksendtime==null)//检查距离上次发送验证码的时间,返回为null说明该邮箱从未发送过验证码,返回数字即为距离上次发送的时间间隔(秒数),300即为5分钟
                {
                    String verifycode = CheckCodeUtil.generateVerifyCode(4);
                    captchaMapper.insertsendemail(email,verifycode);
                    SendMailUtil.SendMailtoUser(email, EmailTitle, context+verifycode);
                    //验证码发送成功
                    sqlSession.close();
                    return 1;
                }
                else{
                    //间隔时间
                    if (checksendtime>=60){
                        String verifycode = CheckCodeUtil.generateVerifyCode(4);
                        captchaMapper.updatesendemail(email,verifycode);
                        SendMailUtil.SendMailtoUser(email, EmailTitle, context+verifycode);
                        //验证码发送成功
                        sqlSession.close();
                        return 2;
                    }
                    else {
                        //距离上次发送时间过短,请隔段时间后重试
                        sqlSession.close();
                        return -1;
                    }
                }
        }
        else {
            //邮箱格式不正确
            sqlSession.close();
            return -2;
        }
    }
    public static Boolean SendMailtoUser(String userEmailAddress,String EmailTitle,String context) throws MessagingException, GeneralSecurityException {
        Properties prop = new Properties();//创建一封邮件
        //以下三项的Key的值都是固定的
        prop.setProperty("mail.host","smtp.qq.com");//设置邮件服务器
        prop.setProperty("mail.transport.protocol","smtp");//设置邮件发送协议
        prop.setProperty("mail.smtp.auth","true");//需要验证用户名和密码
//        prop.put("mail.smtp.starttls.enable", "true");
        //显示debug信息
//        prop.put("mail.debug", "true");
        //如果是QQ邮箱，还要设置SSL加密，加上以下代码即可，其他邮箱不需要
//        MailSSLSocketFactory sf = new MailSSLSocketFactory();
//        sf.setTrustAllHosts(true);
//        prop.put("mail.smtp.ssl.enable","true");
//        prop.put("mail.smtp.ssl.socketFactory",sf);
        //==========使用JavaMail发送邮件的6个步骤======

        //1、创建定义整个应用程序所需要的环境信息的Session对象
        //只有qq才有，其他邮箱不需要
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication(){
                //发件人邮箱的用户名和授权码(只有qq是授权码，其它的是密码)
                return new PasswordAuthentication("abc@example.com","password");
            }
        });
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);

        //2、通过Session得到transport对象
        Transport ts = session.getTransport();

        //3、使用邮箱的用户名和授权码连上邮件服务器
        ts.connect("smtp.163.com","abc@example.com","password");
        //4、创建邮件

        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("abc@example.com"));
        //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(userEmailAddress));
        //邮件的标题
        message.setSubject(EmailTitle);
        //邮件的文本内容  这个可以写css样式<h1 style='color:red'>...</h1>
        message.setContent(context,"text/html;charset=UTF-8");
        //5、发送邮件         地址
        ts.sendMessage(message,message.getAllRecipients());
        //6、关闭连接
        ts.close();
        return true;
    }
}
