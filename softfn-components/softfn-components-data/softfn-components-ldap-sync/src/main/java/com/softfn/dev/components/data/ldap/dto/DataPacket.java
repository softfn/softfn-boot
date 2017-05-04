package com.softfn.dev.components.data.ldap.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p/>
 * DataPacket 数据包 (包含三类：新增、更新、删除)
 * <p/>
 *
 * @author softfn
 */
public class DataPacket<T extends Serializable> implements Serializable {
    /**
     * 新增的数据
     */
    private List<T> newData = Collections.synchronizedList(new ArrayList<T>());
    /**
     * 脏的数据
     */
    private List<T> dirtyData = Collections.synchronizedList(new ArrayList<T>());
    /**
     * 废弃的数据
     */
    private List<T> junkData = Collections.synchronizedList(new ArrayList<T>());

    public List<T> getNewData() {
        return newData;
    }

    public void setNewData(List<T> newData) {
        this.newData = newData;
    }

    public List<T> getDirtyData() {
        return dirtyData;
    }

    public void setDirtyData(List<T> dirtyData) {
        this.dirtyData = dirtyData;
    }

    public List<T> getJunkData() {
        return junkData;
    }

    public void setJunkData(List<T> junkData) {
        this.junkData = junkData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("newData", newData)
                .append("dirtyData", dirtyData)
                .append("junkData", junkData)
                .toString();
    }
}
