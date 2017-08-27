package com.zane.ui.meiwen.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by shizhang on 2017/8/27.
 */

@Entity
public class MeiwEntity {
    @Id
    private Long id;
    @Property(nameInDb = "TITLE")
    private String title;
    @Property(nameInDb = "DATE")
    private String date;
    @Property(nameInDb = "AUTHOR")
    private String author;
    @Generated(hash = 1859331791)
    public MeiwEntity(Long id, String title, String date, String author) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.author = author;
    }
    @Generated(hash = 1430445661)
    public MeiwEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}
