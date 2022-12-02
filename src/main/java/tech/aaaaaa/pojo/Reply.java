package tech.aaaaaa.pojo;

public class Reply {
    private Integer rid;
    private Integer pid;
    private Integer uid;
    private String content;
    private String sendtime;

    @Override
    public String toString() {
        return "Reply{" +
                "rid=" + rid +
                ", pid=" + pid +
                ", uid=" + uid +
                ", content='" + content + '\'' +
                ", sendtime='" + sendtime + '\'' +
                '}';
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }
}
