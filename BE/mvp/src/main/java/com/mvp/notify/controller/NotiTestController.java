//package com.mvp.notify.controller;
//
//import com.mvp.notify.service.NotificationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/test")
//@RequiredArgsConstructor
//public class NotiTestController {
//
//    private final NotificationService notificationService;
//
//    @GetMapping()
//    public void sendMsg(){
//        System.out.println("요청 받음");
//        String content = "data changed";
//        notificationService.send(content,"/temp-url");
//    }
//
//}
