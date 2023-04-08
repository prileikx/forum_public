package tech.aaaaaa.pojo;

import lombok.Data;

@Data
public class Reply {
    private Integer rid;
    private Integer pid;
    private Integer uid;
    private String content;
    private String sendtime;
    private String username;
    private String img;
}
