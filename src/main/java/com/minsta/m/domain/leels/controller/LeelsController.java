package com.minsta.m.domain.leels.controller;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsRequest;
import com.minsta.m.domain.leels.service.CreateLeelsService;
import com.minsta.m.domain.leels.service.LeelsDeleteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leels")
@RequiredArgsConstructor
public class LeelsController {

    private final CreateLeelsService createLeelsService;
    private final LeelsDeleteService leelsDeleteService;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CreateLeelsRequest createLeelsRequest) {
        createLeelsService.execute(createLeelsRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{leelsId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long leelsId) {
        leelsDeleteService.execute(leelsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
