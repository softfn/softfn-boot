package com.softfn.dev.components.media.service.impl;

import com.softfn.dev.common.annotation.InvokeLog;
import com.softfn.dev.common.beans.FileInfo;
import com.softfn.dev.common.exception.ExceptionCode;
import com.softfn.dev.common.exception.UploadFileException;
import com.softfn.dev.common.interfaces.FileService;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * FileServiceImpl 文件服务
 * <p/>
 * 存储基于FastDFS分布式文件系统处理
 *
 * @author softfn
 */
@Service("fileService")
public class FileServiceImpl implements FileService, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    public static final String CLIENT_CONFIG_FILE = "config/fdfs_client.conf";

    @Value("${app.tracker.ngnix}")
    private String trackerNgnix;

    private TrackerClient trackerClient;
    private TrackerServer trackerServer;
    private StorageServer storageServer;
    private StorageClient storageClient;

    @InvokeLog(name = "上传文件", description = "上传文件至FastDFS文件系统")
    @Override
    public String uploadToFastDFS(FileInfo file) {
        logger.info(MessageFormat.format("upload file name: {0} file length: {1}", file.getName()));

        file.validate();

        NameValuePair[] meta_list = buildNameValuePairs(file);
        String[] uploadResults = null;
        try {
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
        } catch (Exception e) {
            throw new UploadFileException(ExceptionCode.UPLOAD_FILE_EXCEPTION, e);
        }
        if (uploadResults == null) {
            throw new UploadFileException(ExceptionCode.UPLOAD_FILE_EXCEPTION,
                    "errorCode: " + String.valueOf(storageClient.getErrorCode()));
        }

        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];
        String fileAbsolutePath = trackerNgnix + "/" + groupName + "/" + remoteFileName;

        logger.info("upload file successfully! " + "group name: " + groupName + ", remote filename: " + remoteFileName);

        return fileAbsolutePath;
    }

    private NameValuePair[] buildNameValuePairs(FileInfo file) {
        NameValuePair[] meta_list = new NameValuePair[6];
        meta_list[0] = new NameValuePair("name", file.getName());
        meta_list[1] = new NameValuePair("ext", file.getExt());
        meta_list[2] = new NameValuePair("length", String.valueOf(file.getContent().length));
        meta_list[3] = new NameValuePair("width", file.getWidth());
        meta_list[4] = new NameValuePair("heigth", file.getHeight());
        meta_list[5] = new NameValuePair("author", file.getAuthor());
        return meta_list;
    }

    @InvokeLog(name = "获取文件元信息", description = "根据文件绝对路径获取FastDFS文件元信息")
    @Override
    public Map<String, String> getMetaFromFastDFS(String fileAbsolutePath) {
        //例："http://201.45.3.43:8080/group1/M00/00/00/wKgBm1N1-CiANRLmAABygPyzdlw073.jpg"
        Assert.isTrue(fileAbsolutePath.length() > trackerNgnix.length() && fileAbsolutePath.startsWith(trackerNgnix), "fileAbsolutePath参数无效");
        String filePath = fileAbsolutePath.substring(trackerNgnix.length() + 1);
        int i = filePath.indexOf("/");
        if (i == -1)
            Assert.isTrue(true, "fileAbsolutePath参数无效");

        try {
            String groupName = filePath.substring(0, i);
            String fileName = filePath.substring(i + 1);
            NameValuePair[] metadata = storageClient.get_metadata(groupName, fileName);
            Map<String, String> fileMeta = new HashMap<>();
            for (NameValuePair pair : metadata) {
                fileMeta.put(pair.getName(), pair.getValue());
            }
            return fileMeta;
        } catch (Exception e) {
            throw new UploadFileException(ExceptionCode.EXCEPTION, e);
        }
    }

    @InvokeLog(name = "删除文件", description = "根据文件绝对路径从FastDFS文件系统中删除")
    @Override
    public void deleteFromFastDFS(String fileAbsolutePath) {
        //例："http://201.45.3.43:8080/group1/M00/00/00/wKgBm1N1-CiANRLmAABygPyzdlw073.jpg"
        Assert.isTrue(fileAbsolutePath.length() > trackerNgnix.length() && fileAbsolutePath.startsWith(trackerNgnix), "fileAbsolutePath参数无效");
        String filePath = fileAbsolutePath.substring(trackerNgnix.length() + 1);
        int i = filePath.indexOf("/");
        if (i == -1)
            Assert.isTrue(true, "fileAbsolutePath参数无效");

        try {
            String groupName = filePath.substring(0, i);
            String fileName = filePath.substring(i + 1);
            storageClient.delete_file(groupName, fileName);
        } catch (Exception e) {
            throw new UploadFileException(ExceptionCode.DELETE_FILE_EXCEPTION, e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("FastDFS configuration file path:" + CLIENT_CONFIG_FILE);
        InputStream inputStream = null;
        try {
            inputStream = new ClassPathResource(CLIENT_CONFIG_FILE).getInputStream();
            ClientGlobal.init(CLIENT_CONFIG_FILE, inputStream);
        } finally {
            if (inputStream != null)
                inputStream.close();
        }

        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();

        storageClient = new StorageClient(trackerServer, storageServer);
    }
}
