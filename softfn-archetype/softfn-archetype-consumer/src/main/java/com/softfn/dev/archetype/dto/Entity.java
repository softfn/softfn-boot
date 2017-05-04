package com.softfn.dev.archetype.dto;

import com.softfn.dev.common.interfaces.StatusCode;

import java.util.Date;

/**
 * <p/>
 * Entity
 * <p/>
 *
 * @author softfn
 */
public class Entity {
//    @JSONField(format="yyyy-MM-dd")
    private Date date = new Date();
    private String str = null;
    private Integer int_ = null;
    private Double double_ = null;
    private Float float_ = null;
    private StatusCode sc = null;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Integer getInt_() {
        return int_;
    }

    public void setInt_(Integer int_) {
        this.int_ = int_;
    }

    public Double getDouble_() {
        return double_;
    }

    public void setDouble_(Double double_) {
        this.double_ = double_;
    }

    public Float getFloat_() {
        return float_;
    }

    public void setFloat_(Float float_) {
        this.float_ = float_;
    }

    public StatusCode getSc() {
        return sc;
    }

    public void setSc(StatusCode sc) {
        this.sc = sc;
    }
}
