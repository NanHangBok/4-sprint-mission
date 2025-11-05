package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.auth.provider.JwtTokenProvider;
import com.sprint.mission.discodeit.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.text.ParseException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SseController {
    private final SseService sseService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/sse")
    public SseEmitter connect(@RequestHeader(value = "Authorization") String accessToken,
                              @RequestHeader(value = "Last-Event-ID", required = false) UUID lastEventId) throws ParseException {
        String token = accessToken.replace("Bearer ", "");
        jwtTokenProvider.verifyJws(token);
        UUID userId = jwtTokenProvider.getUserId(token);
        
        return sseService.connect(userId, lastEventId);
    }
}
