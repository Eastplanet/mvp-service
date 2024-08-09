package com.mvp.notify.service;

import com.mvp.notify.repository.NotificationRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationService {

    //타임아웃 설정
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final NotificationRepository notificationRepository;


    //SSE Emitter를 생성하는 메소드
    private SseEmitter createEmitter(String id) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        //생성된 SSE Emitter를 저장소에 저장
        notificationRepository.save(id, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> notificationRepository.deleteById(id));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> notificationRepository.deleteById(id));

        return emitter;
    }

    public void sendEvent(Object data) {

        List<String> allKey = notificationRepository.getAllKey();
        for(String sendId : allKey){
            // 먼저 클라이언트의 SseEmitter를 가져온다
            SseEmitter emitter = notificationRepository.get(sendId);
            if (emitter != null) {
                try {
                    // 데이터를 클라이언트에게 실어보낸다.
                    emitter.send(SseEmitter.event().id(String.valueOf(sendId)).name("업무수정").data(data));
                } catch (IOException exception) {
                    // 데이터 전송 중 오류가 발생하면 Emitter를 삭제하고 에러를 완료 상태로 처리
                    notificationRepository.deleteById(sendId);
                    emitter.completeWithError(exception);
                }
            }
        }
    }

    public void sendEventOne(String userId, Object data) {

        List<String> allKey = notificationRepository.getAllKey();
        for(String sendId : allKey){
            // 먼저 클라이언트의 SseEmitter를 가져온다
            SseEmitter emitter = notificationRepository.get(sendId);
            if (emitter != null) {
                try {
                    // 데이터를 클라이언트에게 실어보낸다.
                    emitter.send(SseEmitter.event().id(String.valueOf(sendId)).name("업무수정").data(data));
                } catch (IOException exception) {
                    // 데이터 전송 중 오류가 발생하면 Emitter를 삭제하고 에러를 완료 상태로 처리
                    notificationRepository.deleteById(sendId);
                    emitter.completeWithError(exception);
                }
            }
        }
    }

    public SseEmitter subscribe(String userId,final HttpServletResponse response) {
        SseEmitter emitter = createEmitter(userId);
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        sendEventOne(userId, "더미데이터" + userId + "]");
        return emitter;
    }

}