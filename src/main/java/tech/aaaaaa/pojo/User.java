package tech.aaaaaa.pojo;

public class User {
    private Integer uid;
    private String username;
    private String describes;
    private String upassword;
    private String email;
    private Integer ugid;
    private Integer ifconfirmemail;
    private String verify;
    private String registertime;
    private Integer postcount;
    private Integer replycount;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getPostcount() {
        return postcount;
    }

    public void setPostcount(Integer postcount) {
        this.postcount = postcount;
    }

    public Integer getReplycount() {
        return replycount;
    }

    public void setReplycount(Integer replycount) {
        this.replycount = replycount;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUgid() {
        return ugid;
    }

    public void setUgid(Integer ugid) {
        this.ugid = ugid;
    }

    public Integer getIfconfirmemail() {
        return ifconfirmemail;
    }

    public void setIfconfirmemail(Integer ifconfirmemail) {
        this.ifconfirmemail = ifconfirmemail;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getRegistertime() {
        return registertime;
    }

    public void setRegistertime(String registertime) {
        this.registertime = registertime;
    }
}
