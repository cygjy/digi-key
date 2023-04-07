package com.jd.resource.controller;

import com.jd.common.config.JdConfig;
import com.jd.common.core.domain.AjaxResult;
import com.jd.common.utils.StringUtils;
import com.jd.common.utils.file.FileUploadUtils;
import com.jd.common.utils.file.FileUtils;
import com.jd.framework.config.ServerConfig;
import com.jd.resource.service.FileOssUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyang
 */
@RestController
@RequestMapping(value = "/web/oss")
public class FileOssUploadController {


    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private FileOssUploadService fileOssUploadService;

    private static final String FILE_DIVISION = ",";


    /**
     * 文件上传api
     * @param: file
     * @return: com.alibaba.fastjson.JSONObject
     * @author: chenyang
     */
    @PostMapping("/upload")
    public AjaxResult upload(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            return fileOssUploadService.upload(file);
        } else {
            return AjaxResult.error("参数异常！");
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/file/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = JdConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }




    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads/file")
    public AjaxResult uploadFiles(List<MultipartFile> file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = JdConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile files : file)
            {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, files);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(files.getOriginalFilename());
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", StringUtils.join(urls, FILE_DIVISION));
            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DIVISION));
            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DIVISION));
            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DIVISION));
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
}
