package tech.aaaaaa.pojo;

public class Posts {
    private Integer pid;
    private Integer uid;
    private String title;
    private String sendtime;
    private Integer viewcount;
    private Integer replaycount;
    private String edittime;
    private Integer edituid;
    private Integer Integerpcid;
    private String content;

    @Override
    public String toString() {
        return "Posts{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", sendtime='" + sendtime + '\'' +
                ", viewcount=" + viewcount +
                ", replaycount=" + replaycount +
                ", edittime='" + edittime + '\'' +
                ", edituid=" + edituid +
                ", Integerpcid=" + Integerpcid +
                ", content='" + content + '\'' +
                '}';
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public Integer getViewcount() {
        return viewcount;
    }

    public void setViewcount(Integer viewcount) {
        this.viewcount = viewcount;
    }

    public Integer getReplaycount() {
        return replaycount;
    }

    public void setReplaycount(Integer replaycount) {
        this.replaycount = replaycount;
    }

    public String getEdittime() {
        return edittime;
    }

    public void setEdittime(String edittime) {
        this.edittime = edittime;
    }

    public Integer getEdituid() {
        return edituid;
    }

    public void setEdituid(Integer edituid) {
        this.edituid = edituid;
    }

    public Integer getIntegerpcid() {
        return Integerpcid;
    }

    public void setIntegerpcid(Integer integerpcid) {
        Integerpcid = integerpcid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
