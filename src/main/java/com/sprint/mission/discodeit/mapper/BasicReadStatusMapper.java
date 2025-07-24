//package com.sprint.mission.discodeit.mapper;
//
//import com.sprint.mission.discodeit.dto.ReadStatusCreateDto;
//import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
//import com.sprint.mission.discodeit.dto.ReadStatusDto;
//import com.sprint.mission.discodeit.entity.ReadStatus;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//import java.util.UUID;
//
//@Component
//public class BasicReadStatusMapper {
//    public ReadStatus toReadStatus(ReadStatusCreateRequest readStatusCreateRequest) {
//        return new ReadStatus(readStatusCreateRequest.userId(), readStatusCreateRequest.channelId(), Instant.now());
//    }
//
//    public ReadStatusDto toReadStatusDto(ReadStatus readStatus) {
//        return new ReadStatusDto(readStatus.getId(), readStatus.getUserId(), readStatus.getChannelId(), readStatus.getLastReadAt());
//    }
//
//    public ReadStatusCreateDto ofReadStatusCreateDto(UUID channelId, ReadStatusCreateRequest readStatusCreateRequest) {
//        return new ReadStatusCreateDto(channelId, readStatusCreateRequest.userId());
//    }
//}
