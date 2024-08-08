package com.mvp.notify.controller;

import com.mvp.common.ResponseDto;
import com.mvp.common.exception.StatusCode;
import com.mvp.notify.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notify")
@Log4j2
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe/{email}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<?> subscribe(@PathVariable String email, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId){
        return ResponseDto.response(StatusCode.SUCCESS,notificationService.subscribe(email, lastEventId));
    }

}