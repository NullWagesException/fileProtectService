package com.zf.pojo;

import java.util.Date;

public class Uplevel {

    private Integer id;
    private String username;
    private Date date;
    private Integer oldlevel;
    private Integer newlevel;
    private Integer allow;
    private Integer userid;

    @Override
    public String toString() {
        return "Uplevel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", oldlevel=" + oldlevel +
                ", newlevel=" + newlevel +
                ", allow=" + allow +
                ", userid=" + userid +
                '}';
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getAllow() {
        return allow;
    }

    public void setAllow(Integer allow) {
        this.allow = allow;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getOldlevel() {
        return oldlevel;
    }

    public void setOldlevel(Integer oldlevel) {
        this.oldlevel = oldlevel;
    }

    public Integer getNewlevel() {
        return newlevel;
    }

    public void setNewlevel(Integer newlevel) {
        this.newlevel = newlevel;
    }
}
