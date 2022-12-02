package tech.aaaaaa.pojo;

public class Collect {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private String datetime;

    @Override
    public String toString() {
        return "Collect{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", datetime='" + datetime + '\'' +
                '}';
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
