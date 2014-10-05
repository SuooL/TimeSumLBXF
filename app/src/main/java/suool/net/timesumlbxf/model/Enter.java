package suool.net.timesumlbxf.model;

/**
 * Created by SuooL on 2014/10/5.
 */
public class Enter {
    private int id;
    private String enterName;
    private double enterTime;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setEnterName(String enterName) {
        this.enterName = enterName;
    }

    public String getEnterName() {
        return enterName;
    }

    public void setEnterTime(double enterTime) {
        this.enterTime = enterTime;
    }

    public double getEnterTime() {
        return enterTime;
    }
}
