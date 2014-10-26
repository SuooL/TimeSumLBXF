package suool.net.timesumlbxf.model;

import suool.net.timesumlbxf.ui.SlideView;

/**
 * 任务类
 *
 * Created by SuooL on 2014/10/8.
 */
public class MissionItem {

    /**
     * 记录基本信息属性
     * 类别
     * 名称
     * 日期
     * 时间长度
     * 图片id
     */
    private String parentCategory;
    private String subCategory;
    private String category;
    private String date;
    private String memo;
    private double timeLength;
    private int imageId;

    /**
     * 构造函数
     */
    public MissionItem()
    {
    }

    // 实例方法


    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(double timeLength) {
        this.timeLength = timeLength;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

}
