package com.minsta.m.domain.file.controller;

import com.minsta.m.domain.file.controller.data.response.FileUploadResponse;
import com.minsta.m.domain.file.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "http://10.53.68.120:80/file 하위 API", description = "File 관련 API")
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadService fileUploadService;


    @Operation(summary = "create aws url", description = "파일 aws url")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "CREATED",
                    content = @Content(schema = @Schema(implementation = FileUploadResponse.class)),
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)
            ),
            @ApiResponse(responseCode = "400", description = "Not Allowed File Format"),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })

    @PostMapping()
    public ResponseEntity<FileUploadResponse> fileUpload(
            @RequestPart(value = "file", required = true) MultipartFile file
    ) {
        var res = fileUploadService.execute(file);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
