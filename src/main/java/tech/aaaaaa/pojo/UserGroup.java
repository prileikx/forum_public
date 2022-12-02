package tech.aaaaaa.pojo;

public class UserGroup {
    private Integer ugid;
    private String usergroup;
    private Integer limits;

    @Override
    public String toString() {
        return "UserGroup{" +
                "ugid=" + ugid +
                ", usergroup='" + usergroup + '\'' +
                ", limits=" + limits +
                '}';
    }

    public Integer getUgid() {
        return ugid;
    }

    public void setUgid(Integer ugid) {
        this.ugid = ugid;
    }

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }

    public Integer getLimits() {
        return limits;
    }

    public void setLimits(Integer limits) {
        this.limits = limits;
    }
}
