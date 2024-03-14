package com.minsta.m.domain.leels.controller;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsRequest;
import com.minsta.m.domain.leels.service.CancelLeelsLikeService;
import com.minsta.m.domain.leels.service.CreateLeelsLikeService;
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
    private final CreateLeelsLikeService createLeelsLikeService;
    private final CancelLeelsLikeService cancelLeelsLikeService;

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

    @PostMapping("/{leelsId}")
    public ResponseEntity<HttpStatus> like(@PathVariable Long leelsId) {
        createLeelsLikeService.execute(leelsId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{leelsId}")
    public ResponseEntity<HttpStatus> cancel(@PathVariable Long leelsId) {
        cancelLeelsLikeService.execute(leelsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
