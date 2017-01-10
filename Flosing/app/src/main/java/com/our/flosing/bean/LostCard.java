package com.our.flosing.bean;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

/**
 * Created by huangrui on 2016/12/28.
 */

/**寻物启示帖**/
public class LostCard {
    private String id;
    private String type;
    private String name;
    private String title;
    private String description;
    private Date sDate;
    private Date eDate;
    private Boolean isFinish;
    private String contactWay;
    private String contactDetail;
    private List<String> picUrls;
    private List<Bitmap> pics;

    public LostCard() {
        this.isFinish = false;
    }

    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return this.type; }
    public void setType(String type) { this.type = type; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription()  { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public Date getSDate() { return this.sDate; }
    public void setSDate(Date sDate) { this.sDate = sDate; }

    public Date getEDate() { return this.eDate; }
    public void setEDate(Date eDate) { this.eDate = eDate; }

    public Boolean getIsFinish() { return this.isFinish; }
    public void setIsFinish(Boolean isFinish) { this.isFinish = isFinish; }

    public String getContactWay() { return this.contactWay; }
    public void setContactWay(String contactWay) { this.contactWay = contactWay; }

    public String getContactDetail()  { return this.contactDetail; }
    public void setContactDetail(String contactDetail) { this.contactDetail = contactDetail; }

    public List<String> getPicUrls() { return this.picUrls; }
    public void setPicUrls(List<String> picUrls) { this.picUrls = picUrls; }

    public List<Bitmap> getPics() { return this.pics; }
    public void setPics(List<Bitmap> pics) {
        this.pics = pics;
    }
}
