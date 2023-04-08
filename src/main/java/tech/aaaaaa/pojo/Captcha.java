package tech.aaaaaa.pojo;

import lombok.Data;

@Data
public class Captcha {
    private Integer ctid;
    private Integer uid;
    private String email;
    private String captcha;
    private String sendtime;
}
