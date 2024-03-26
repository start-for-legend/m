package com.minsta.m.domain.file.service;

import com.minsta.m.domain.file.controller.data.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    FileUploadResponse execute(MultipartFile file);
}
