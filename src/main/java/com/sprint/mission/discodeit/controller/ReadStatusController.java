package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ReadStatusCreateDto;
import com.sprint.mission.discodeit.dto.ReadStatusPostDto;
import com.sprint.mission.discodeit.dto.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.ReadStatusUpdateDto;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.ReadStatusService;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/read-statuses")
public class ReadStatusController {
    private final ReadStatusService readStatusService;
    private final ReadStatusMapper readStatusMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/{channel-id}")
    public ResponseEntity createChannelReadStatus(@PathVariable("channel-id") UUID channelId, @RequestBody ReadStatusPostDto readStatusPostDto) {
        ReadStatusCreateDto readStatusCreateDto = new ReadStatusCreateDto(channelId, readStatusPostDto.userId());
        ReadStatusResponseDto response = readStatusService.create(readStatusCreateDto);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{channel-id}")
    public ResponseEntity updateChannelReadStatus(@PathVariable("channel-id") UUID channelId, @RequestBody ReadStatusUpdateDto readStatusUpdateDto) {
        ReadStatusResponseDto response = readStatusService.update(channelId, readStatusUpdateDto);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{user-id}")
    public ResponseEntity findReadStatusByUserId(@PathVariable("user-id") UUID userId) {
        List<ReadStatusResponseDto> response = readStatusService.findAllByUserId(userId);

        return ResponseEntity.ok(response);
    }
}
