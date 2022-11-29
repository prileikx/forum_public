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

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", describes='" + describes + '\'' +
                ", upassword='" + upassword + '\'' +
                ", email='" + email + '\'' +
                ", ugid=" + ugid +
                ", ifconfirmemail=" + ifconfirmemail +
                ", verify='" + verify + '\'' +
                ", registertime=" + registertime +
                '}';
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
