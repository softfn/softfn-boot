package com.softfn.dev.common.beans;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p/>
 * FileInfo 上传文件描述类
 * <p/>
 * <p/>
 * 用于基于FastDFS分布式文件系统上传附件的包装类
 *
 * @author softfn
 */
public class FileInfo extends BaseRequest {
    /**
     * 文件名
     */
    @NotEmpty(message = "文件名不能为空")
    private String name;
    /**
     * 文件扩展名
     */
    @NotEmpty(message = "文件扩展名不能为空")
    private String ext;
    /**
     * 文件内容
     */
    @NotEmpty(message = "文件内容不能为空")
    private byte[] content;
    /**
     * 文件高度
     */
    private String height;
    /**
     * 文件宽度
     */
    private String width;
    /**
     * 文件作者（拥有人）
     */
    private String author;

    public FileInfo() {
    }

    public FileInfo(String name, String ext, byte[] content) {
        this.name = name;
        this.ext = ext;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("ext", ext)
                .append("content", content)
                .append("height", height)
                .append("width", width)
                .append("author", author)
                .toString();
    }
}
