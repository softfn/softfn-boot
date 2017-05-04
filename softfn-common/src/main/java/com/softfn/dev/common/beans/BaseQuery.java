package com.softfn.dev.common.beans;

import com.softfn.dev.common.constants.Direction;
import com.softfn.dev.common.util.lang.CamelCaseUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p/>
 * BaseQuery 查询参数基类
 * <p/>
 *
 * @author softfn
 */
public class BaseQuery<T extends BaseQuery> extends BaseRequest {
    private List<SortOrder> sorters = new ArrayList<SortOrder>();

    public T addSortOrderAsc(String property) {
        sorters.add(new SortOrder(property, Direction.ASC));
        return (T) this;
    }

    public T addSortOrderDesc(String property) {
        sorters.add(new SortOrder(property, Direction.DESC));
        return (T) this;
    }

    public List<SortOrder> getSorters() {
        return sorters;
    }

    public T setSorters(List<SortOrder> sorters) {
        this.sorters = sorters;
        return (T) this;
    }

    public String getOrderByClause() {
        if (sorters.size() == 0) {
            return null;
        }
        StringBuffer orderClause = new StringBuffer();
        for (Iterator<SortOrder> iterator = sorters.iterator(); iterator.hasNext(); ) {
            SortOrder order = iterator.next();
            orderClause.append(propertyToSqlFiled(order.getProperty()))
                    .append(" ").append(order.getDirection().name());
            if (iterator.hasNext())
                orderClause.append(",");
        }
        return orderClause.toString();
    }

    private String propertyToSqlFiled(String property) {
        return CamelCaseUtils.toUnderlineName(property).toUpperCase();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sorters", sorters)
                .toString();
    }
}
