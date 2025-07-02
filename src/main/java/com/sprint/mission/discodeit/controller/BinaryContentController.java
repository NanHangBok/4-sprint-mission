package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentResponseDto;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/binary-contents")
public class BinaryContentController {
    private final BinaryContentService binaryContentService;
    private final MessageService messageService;
    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/users/{user-id}")
    public ResponseEntity findBinaryContent(@PathVariable("user-id") UUID userId) {
        UUID id = userService.findUserById(userId)
                .profile();
        BinaryContentResponseDto response = binaryContentService.find(id);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/messages/{message-id}")
    public ResponseEntity findBinaryContents(@PathVariable("message-id") UUID messageId) {
        List<UUID> ids = messageService.getMessagesById(messageId).attachmentIds();
        List<BinaryContentResponseDto> response = binaryContentService.findAllByIdIn(ids);
        return ResponseEntity.ok(response);
    }
}
