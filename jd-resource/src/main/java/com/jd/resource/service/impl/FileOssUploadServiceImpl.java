package com.jd.resource.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.jd.common.core.domain.AjaxResult;
import com.jd.common.utils.uuid.UUID;
import com.jd.resource.config.AliyunOssConfig;
import com.jd.resource.service.FileOssUploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyang
 */
@Service
public class FileOssUploadServiceImpl implements FileOssUploadService {

    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png"};

    @Autowired
    private OSS ossClient;
    @Autowired
    private AliyunOssConfig aliyunOssConfig;


    @Override
    public AjaxResult upload(MultipartFile uploadFile) {
        // 获取oss的Bucket名称
        String bucketName = aliyunOssConfig.getBucketName();
        // 获取oss的地域节点
        String endpoint = aliyunOssConfig.getEndPoint();
        // 返回图片上传后返回的url
        String returnImageUrl = "";

        // 校验图片格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            return AjaxResult.error("图片格式不合法");
        }
        // 获取文件原名称
        String originalFilename = uploadFile.getOriginalFilename();
        // 获取文件类型
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 新文件名称
        String newFileName = UUID.randomUUID().toString() + fileType;
        // 构建日期路径, 例如：OSS目标文件夹/2020/10/31/文件名
        String filePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        // 文件上传的路径地址
        String uploadImageUrl =  filePath + "/" + newFileName;

        // 获取文件输入流
        InputStream inputStream = null;
        try {
            inputStream = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 下面两行代码是重点坑：
         * 现在阿里云OSS 默认图片上传ContentType是image/jpeg
         * 也就是说，获取图片链接后，图片是下载链接，而并非在线浏览链接，
         * 因此，这里在上传的时候要解决ContentType的问题，将其改为image/jpg
         */
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("image/jpg");

        //文件上传至阿里云OSS
        ossClient.putObject(bucketName, uploadImageUrl, inputStream, meta);

        ossClient.shutdown();
        returnImageUrl = "http://" + bucketName + "." + endpoint + "/" + uploadImageUrl;
        Map<String,String> map  = new HashMap<>(8);
        map.put("returnImageUrl",returnImageUrl);
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("msg","上传成功!");
        ajaxResult.put("code",200);
        ajaxResult.put("data",map);
        return ajaxResult;
    }
}
