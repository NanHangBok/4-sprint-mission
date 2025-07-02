package com.sprint.mission.discodeit.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channels")
public class ChannelController {
    private final ChannelService channelService;
    private final ChannelMapper channelMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/public")
    public ResponseEntity createPublicChannel(@RequestBody ChannelPostDto channelPostDto) {
        ChannelPublicCreateResponseDto response = channelService.createPublicChannel(channelPostDto);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/private")
    public ResponseEntity createPrivateChannel(@RequestBody ChannelPrivatePostDto channelPrivatePostDto) {
        ChannelPrivateCreateResponseDto response = channelService.createPrivateChannel(channelPrivatePostDto);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{channel-id}")
    public ResponseEntity updatePublicChannel(@PathVariable("channel-id") UUID channelId, @RequestBody ChannelUpdateDto channelUpdateDto) {
        ChannelResponseDto response = channelService.updateChannel(channelId, channelUpdateDto);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{channel-id}")
    public ResponseEntity deletePublicChannel(@PathVariable("channel-id") UUID channelId) {
        channelService.deleteChannel(channelId);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{user-id}/user-channels")
    public ResponseEntity findChannelByUserId(@PathVariable("user-id") UUID userId) {
        List<ChannelResponseDto> responses = channelService.findAllByUserId(userId);

        return ResponseEntity.ok(responses);
    }
}
