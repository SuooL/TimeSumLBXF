package suool.net.timesumlbxf.model;

/**
 * Created by SuooL on 2014/10/5.
 */
public class Work {
    private int id;
    private String workName;
    private double workTime;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkTime(double workTime) {
        this.workTime = workTime;
    }

    public double getWorkTime() {
        return workTime;
    }
}
