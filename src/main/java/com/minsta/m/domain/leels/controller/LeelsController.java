package com.minsta.m.domain.leels.controller;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsRequest;
import com.minsta.m.domain.leels.service.CreateLeelsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leels")
@RequiredArgsConstructor
public class LeelsController {

    private final CreateLeelsService createLeelsService;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CreateLeelsRequest createLeelsRequest) {
        createLeelsService.execute(createLeelsRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
