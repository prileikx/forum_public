package tech.aaaaaa.pojo;

public class PostClass {
    private Integer pcid;
    private String pcname;
    private Integer limits;

    @Override
    public String toString() {
        return "PostClass{" +
                "pcid=" + pcid +
                ", pcname='" + pcname + '\'' +
                ", limits=" + limits +
                '}';
    }

    public Integer getPcid() {
        return pcid;
    }

    public void setPcid(Integer pcid) {
        this.pcid = pcid;
    }

    public String getPcname() {
        return pcname;
    }

    public void setPcname(String pcname) {
        this.pcname = pcname;
    }

    public Integer getLimits() {
        return limits;
    }

    public void setLimits(Integer limits) {
        this.limits = limits;
    }
}
