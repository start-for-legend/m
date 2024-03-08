package com.minsta.m.domain.file.controller;

import com.minsta.m.domain.file.controller.data.response.FileUploadResponse;
import com.minsta.m.domain.file.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadService fileUploadService;

    @PostMapping()
    public ResponseEntity<FileUploadResponse> fileUpload(
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        var res = fileUploadService.execute(file);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
