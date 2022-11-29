package tech.aaaaaa.pojo;

public class Blogs {
    private Integer bid;
    private Integer uid;
    private String content;
    private String send_time;

    @Override
    public String toString() {
        return "bolgs{" +
                "bid=" + bid +
                ", uid=" + uid +
                ", content='" + content + '\'' +
                ", send_time=" + send_time +
                '}';
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
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

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }
}
