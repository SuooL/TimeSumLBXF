package suool.net.timesumlbxf.model;

/**
 * Created by SuooL on 2014/10/5.
 */
public class Study {
    private int id;
    private String studyName;
    private double studyTime;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyTime(double studyTime) {
        this.studyTime = studyTime;
    }

    public double getStudyTime() {
        return studyTime;
    }
}
