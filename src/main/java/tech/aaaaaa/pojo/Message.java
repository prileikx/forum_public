package tech.aaaaaa.pojo;

public class Message {
    private Integer mid;
    private String msg;
    private Integer uid;
    private String sendtime;
    private Integer pid;
    private Integer whoreplyuid;
    private String username;
    private String title;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWhoreplyuid() {
        return whoreplyuid;
    }

    public void setWhoreplyuid(Integer whoreplyuid) {
        this.whoreplyuid = whoreplyuid;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mid=" + mid +
                ", msg='" + msg + '\'' +
                ", uid=" + uid +
                '}';
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
