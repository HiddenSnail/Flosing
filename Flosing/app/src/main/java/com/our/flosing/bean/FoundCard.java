package com.our.flosing.bean;

import java.util.Date;

/**
 * Created by huangrui on 2016/12/28.
 */

public class FoundCard {
    private String type;
    private String name;
    private String title;
    private String description;
    private Date sDate;
    private Date eDate;

    String getType() { return this.type; }
    void setType(String type) { this.type = type; }

    String getName() { return this.name; }
    void setName(String name) { this.name = name; }

    String getTitle() { return this.title; }
    void setTitle(String title) { this.title = title; }

    String getDescription()  { return this.description; }
    void setDescription(String description) { this.description = description; }

    Date getSDate() { return this.sDate; }
    void setSDate(Date sDate) { this.sDate = sDate; }

    Date getEDate() { return this.eDate; }
    void setEDate(Date eDate) { this.eDate = eDate; }
}
