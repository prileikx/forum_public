package tech.aaaaaa.pojo;

public class Message {
    private Integer mid;
    private String msg;
    private Integer uid;

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
