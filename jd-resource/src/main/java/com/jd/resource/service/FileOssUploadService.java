package com.jd.resource.service;

import com.jd.common.core.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenyang
 */
public interface FileOssUploadService {

    /**
     * 上传文件
     * @param file
     * @return
     */
    AjaxResult upload(MultipartFile file);
}
