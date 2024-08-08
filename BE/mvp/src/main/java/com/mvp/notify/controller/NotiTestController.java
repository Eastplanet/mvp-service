package com.mvp.notify.controller;

import com.mvp.notify.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class NotiTestController {

    private final NotificationService notificationService;

    @GetMapping()
    public void sendMsg(){

        String content = "테스트메시지입니다.";
        notificationService.send(content,"/temp-url");
    }

}
