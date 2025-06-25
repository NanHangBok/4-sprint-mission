package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ReadStatusPostDto;
import com.sprint.mission.discodeit.dto.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.ReadStatusUpdateDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ReadStatusMapper {
    public ReadStatus toReadStatus(ReadStatusPostDto postDto){
        return new ReadStatus(postDto.userId(),postDto.channelId(), Instant.now());
    }

    public ReadStatusResponseDto toReadStatusResponseDto(ReadStatus readStatus){
        return new ReadStatusResponseDto(readStatus.getId(), readStatus.getUserId(), readStatus.getChannelId(), readStatus.getLatestTime());
    }
}
