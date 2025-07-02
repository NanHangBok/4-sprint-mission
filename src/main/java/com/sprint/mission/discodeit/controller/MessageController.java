package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final BinaryContentMapper binaryContentMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(MessagePostDto messagePostDto) {
        List<BinaryContentPostDto> binaryContentPostDtos = new ArrayList<>();
        if (!messagePostDto.attachments().get(0).isEmpty()){
            for (int i =0; i < messagePostDto.attachments().size(); i++) {
                BinaryContentPostDto binaryContentPostDto = binaryContentMapper.ofBinaryContentPostDto(messagePostDto.binaryContentTypes().get(i),messagePostDto.attachments().get(i));
                binaryContentPostDtos.add(binaryContentPostDto);
            }
        }
        MessageCreateDto messageCreateDto = messageMapper.ofMessageCreateDto(messagePostDto,binaryContentPostDtos);
        MessageResponseDto response = messageService.createMessage(messageCreateDto);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{message-id}")
    public ResponseEntity update(@PathVariable("message-id")UUID messageId, @RequestBody MessageUpdateDto messageUpdateDto) {
        MessageResponseDto response = messageService.updateMessage(messageId,messageUpdateDto);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{message-id}")
    public ResponseEntity delete(@PathVariable("message-id")UUID messageId) {
        messageService.removeMessage(messageId);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAllByChannelId(@RequestParam UUID channelId) {
        List<MessageResponseDto> responses = messageService.findallByChannelId(channelId);

        return ResponseEntity.ok(responses);
    }
}
