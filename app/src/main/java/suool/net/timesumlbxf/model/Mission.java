package suool.net.timesumlbxf.model;

/**
 * 任务类
 *
 * Created by SuooL on 2014/10/8.
 */
public class Mission {

    /**
     * 任务基本信息属性
     * 类别
     * 名称
     * 时间长度
     * 图片id
     */
    private String category;
    private String name;
    private double timeLength;
    private int imageId;

    /**
     * 构造函数
     * @param category 类别
     * @param name 名称
     * @param timeLength 时间长度
     * @param imageId 图片id
     */
    public Mission(String category, String name, double timeLength, int imageId) {
        this.category = category;
        this.name = name;
        this.timeLength = timeLength;
        this.imageId = imageId;
    }

    // 实例方法
    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public double getTimeLength() {
        return timeLength;
    }

    public int getImageId() {
        return imageId;
    }

}
