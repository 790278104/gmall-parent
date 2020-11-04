package com.atguigu.gmall.product.controller;


import com.atguigu.gmall.common.result.Result;
import io.swagger.annotations.Api;
import org.apache.commons.io.FilenameUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("admin/product")
public class FireUploadController {
    //做回显使用
    @Value("${fileServer.url}")
    private String fileUrl; //赋值 fileUrl =http://192.168.200.128:8080/

    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws IOException, MyException {
        /*
        1.先读取配置文件tracker.conf
        2.数据初始化
        3.创建tracker
        4.创建storage
        5.执行上传
         */
        String configFile = this.getClass().getResource("/tracker.conf").getFile();
        String path = "";
        //判断
        if (configFile!=null){
            //数据初始化
            ClientGlobal.init(configFile);
            //创建trackerClent对象
            TrackerClient trackerClient = new TrackerClient();
            //获取到TrackerServer
            TrackerServer trackerServer = trackerClient.getConnection();
            //创建stroage
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,null);
            //上传文件
            //获取文件后缀名
            String exName = FilenameUtils.getExtension(file.getOriginalFilename());
            path = storageClient1.upload_appender_file1(file.getBytes(), exName, null);

            System.out.println("文件路径：\t" + path);
        }
        return Result.ok(fileUrl+path);

    }
}
