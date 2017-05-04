package com.softfn.dev.common.beans;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <p/>
 * PageQuery 分页查询参数描述类
 * <p/>
 *
 * @author softfn
 */
public class PageQuery<T extends PageQuery> extends BaseQuery<T> {
    /**
     * 默认起始页
     */
    public static final int DEFAULT_PAGE_NUM = 1;
    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 12;
    /**
     * 防止误传入过大分页数 影响性能
     */
    private static final int MAX_PAGE_SIZE = 1000;
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 每页的数量
     */
    private Integer pageSize;
    /**
     * 请求路径
     */
    @NotEmpty(message = "请求路径'requestUrl'为空")
    private String requestUrl;

    //    @NotNull(message = "参数'pageNum'为NULL")
    public Integer getPageNum() {
        return pageNum == null ? pageNum = DEFAULT_PAGE_NUM : pageNum;
    }

    public T setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return (T) this;
    }

    //    @NotNull(message = "参数'pageSize'为NULL")
    public Integer getPageSize() {
        if (pageSize == null)
            pageSize = DEFAULT_PAGE_SIZE;
        else if (pageSize > MAX_PAGE_SIZE)
            pageSize = MAX_PAGE_SIZE;
        return pageSize;
    }

    public T setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return (T) this;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public T setRequestUrl(String requestUrl) {
        int i = requestUrl.indexOf("?");
        if (i > -1) {
            StringBuffer paramstr = new StringBuffer();
            List<String> params = Arrays.asList(requestUrl.substring(i + 1).split("&"));
            for (Iterator<String> iterator = params.iterator(); iterator.hasNext(); ) {
                String param = iterator.next();
                if (!param.startsWith("pageNum"))
                    paramstr.append("&").append(param);
            }
            if (paramstr.length() > 0) {
                paramstr.deleteCharAt(0);
                requestUrl = requestUrl.substring(0, i + 1) + paramstr.toString();
            } else {
                requestUrl = requestUrl.substring(0, i);
            }

        }
        this.requestUrl = requestUrl;
        return (T) this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pageNum", pageNum)
                .append("pageSize", pageSize)
                .append("requestUrl", requestUrl)
                .toString();
    }
}
