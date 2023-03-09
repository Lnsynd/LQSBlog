package com.lqs.service;

import com.lqs.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService  {
    ResponseResult uploadImg(MultipartFile file);
}
