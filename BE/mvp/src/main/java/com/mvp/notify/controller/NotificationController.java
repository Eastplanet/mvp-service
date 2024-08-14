package com.mvp.notify.controller;

import com.mvp.notify.service.NotificationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notify")
@Log4j2
public class NotificationController {

    private final NotificationService notificationService;

    //구독 페이지
    @GetMapping(value = "/subscribe/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable String id, HttpServletResponse response) {
        return notificationService.subscribe(id, response);
    }

    @PostMapping("/send-data")
    public void sendData() {
        notificationService.sendEvent("data");
    }

}