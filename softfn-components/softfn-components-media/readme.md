媒体文件处理
-----------

1） 文件上传、下载、删除
    
    Spring XML引入配置
    
        <!--文件服务-->
        <dubbo:reference id="fileService" interface="FileService"/>
        
        例：
            @RequestMapping(value = "/answer/uploadfile", method = RequestMethod.POST)
            @InvokeLog(name = "调用uploadFile", description = "上传附件")
            @ResponseBody
            public BaseResponse<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
                String path = "";
                String originalFilename = file.getOriginalFilename();
                if (StringUtils.hasText(originalFilename)) {
                    String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                    try {
                        path = fileService.uploadToFastDFS(new FileInfo(originalFilename, extName, file.getBytes()));
                    } catch (IOException e) {
                        throw new RuntimeException("上传失败", e);
                    }
                }
                return new BaseResponse<>(path);
            }

2） 图片文件大小转换
    待完善。。。