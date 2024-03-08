package com.minsta.m.domain.file.controller.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FileUploadResponse {

    private String awsUrl;
}