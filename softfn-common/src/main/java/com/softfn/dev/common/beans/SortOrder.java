package com.softfn.dev.common.beans;

import com.softfn.dev.common.constants.Direction;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * <p/>
 * SortOrder  排序
 * <p/>
 *
 * @author softfn
 */
public class SortOrder implements Serializable {
    /**
     * 排序字段
     */
    private String property;
    /**
     * 排序方式
     */
    private Direction direction;

    public SortOrder() {
    }

    public SortOrder(String property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("property", property)
                .append("direction", direction)
                .toString();
    }
}
