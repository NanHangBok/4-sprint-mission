package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ReadStatusPostDto;
import com.sprint.mission.discodeit.dto.ReadStatusUpdateDto;
import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;
import java.util.UUID;

public interface ReadStatusService {
    ReadStatus create(ReadStatusPostDto readStatusPostDto);
    ReadStatus find(UUID id);
    List<ReadStatus> findAllByUserId(UUID userId);
    void update(ReadStatusUpdateDto readStatusUpdateDto);
    void delete(UUID id);
    //
    List<ReadStatus> findAll();
}
