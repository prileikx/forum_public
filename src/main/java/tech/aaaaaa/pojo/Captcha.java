package tech.aaaaaa.pojo;

public class Captcha {
    private Integer ctid;
    private Integer uid;
    private String email;
    private String captcha;
    private String sendtime;

    @Override
    public String toString() {
        return "captcha{" +
                "ctid=" + ctid +
                ", uid=" + uid +
                ", email='" + email + '\'' +
                ", captcha='" + captcha + '\'' +
                ", sendtime='" + sendtime + '\'' +
                '}';
    }

    public Integer getCtid() {
        return ctid;
    }

    public void setCtid(Integer ctid) {
        this.ctid = ctid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }
}
