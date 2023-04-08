package tech.aaaaaa.pojo;

import lombok.Data;

@Data
public class Posts {
    private Integer pid;
    private Integer uid;
    private String title;
    private String sendtime;
    private Integer viewcount;
    private Integer replycount;
    private String edittime;
    private Integer edituid;
    private Integer pcid;
    private String content;
    private String username;
    private String img;
}
