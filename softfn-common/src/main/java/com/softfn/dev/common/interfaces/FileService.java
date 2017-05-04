package com.softfn.dev.common.interfaces;

import com.softfn.dev.common.beans.FileInfo;

import java.util.Map;

/**
 * <p/>
 * FileService 文件服务
 * <p/>
 * 存储基于FastDFS分布式文件系统处理
 *
 * @author softfn
 */
public interface FileService {
    /**
     * 文件上传 至 FastDFS
     *
     * @param file 文件对象
     * @return 返回 存储绝对路径
     * 例："http://201.45.3.43:8080/group1/M00/00/00/wKgBm1N1-CiANRLmAABygPyzdlw073.jpg"
     */
    String uploadToFastDFS(FileInfo file);

    /**
     * 从FastDFS文件系统获取文件元信息
     *
     * @param fileAbsolutePath 存储绝对路径
     * @return
     */
    Map<String, String> getMetaFromFastDFS(String fileAbsolutePath);

    /**
     * 根据分组名称及文件名从FastDFS文件系统中删除
     *
     * @param fileAbsolutePath 存储绝对路径
     */
    void deleteFromFastDFS(String fileAbsolutePath);
}
