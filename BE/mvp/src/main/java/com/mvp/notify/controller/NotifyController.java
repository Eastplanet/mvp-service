package com.mvp.notify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notify")
public class NotifyController {
    private final NotifyService notifyService;
⠀
    public NotifyController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }
⠀
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal User principal,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notifyService.subscribe(principal.getUsername(), lastEventId);
    }
}