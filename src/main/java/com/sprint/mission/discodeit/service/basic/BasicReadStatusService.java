package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusCreateDto;
import com.sprint.mission.discodeit.dto.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.ReadStatusUpdateDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ReadStatusMapper readStatusMapper;

    @Override
    public ReadStatusResponseDto create(ReadStatusCreateDto readStatusCreateDto) {
        validateUser(readStatusCreateDto);
        validateChannel(readStatusCreateDto);
        existsByUserAndChannel(readStatusCreateDto);

        ReadStatus readStatus = new ReadStatus(readStatusCreateDto.userId(), readStatusCreateDto.channelId(), Instant.now());
        readStatusRepository.save(readStatus);
        ReadStatusResponseDto readStatusResponseDto = readStatusMapper.toReadStatusResponseDto(readStatus);
        return readStatusResponseDto;
    }

    @Override
    public ReadStatusResponseDto find(UUID id) {
        return readStatusMapper.toReadStatusResponseDto(readStatusRepository.findById(id));
    }

    @Override
    public List<ReadStatusResponseDto> findAllByUserId(UUID userId) {
        List<ReadStatusResponseDto> readStatusResponseDtos = new ArrayList<>();
        readStatusRepository.findAll().stream()
                .filter(readStatus -> readStatus.getUserId().equals(userId))
                .forEach(readStatus -> readStatusResponseDtos.add(readStatusMapper.toReadStatusResponseDto(readStatus)));
        return readStatusResponseDtos;
    }

    @Override
    public ReadStatusResponseDto update(UUID id, ReadStatusUpdateDto readStatusUpdateDto) {
        ReadStatus findReadStatus = readStatusRepository.findByChannelIdAndUserId(id,readStatusUpdateDto.userId());

        Optional.ofNullable(readStatusUpdateDto.latestTime()).ifPresent(findReadStatus::setLatestTime);
        readStatusRepository.save(findReadStatus);
        return readStatusMapper.toReadStatusResponseDto(findReadStatus);
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.delete(id);
    }

    public ReadStatusResponseDto updateByChannelId(UUID channelId, ReadStatusUpdateDto readStatusUpdateDto) {
        List<ReadStatusResponseDto> readStatusResponseDtos = new ArrayList<>();
        ReadStatus findReadStatus = readStatusRepository.findByChannelIdAndUserId(channelId,readStatusUpdateDto.userId());

        Optional.ofNullable(readStatusUpdateDto.latestTime()).ifPresent(findReadStatus::setLatestTime);
        readStatusRepository.save(findReadStatus);
        return readStatusMapper.toReadStatusResponseDto(readStatusRepository.findById(findReadStatus.getId()));
    }
    // 테스트용 findAll()
    public List<ReadStatusResponseDto> findAll() {
        List<ReadStatusResponseDto> readStatusResponseDtos = new ArrayList<>();
        readStatusRepository.findAll().stream()
                .forEach(readStatus -> readStatusResponseDtos.add(readStatusMapper.toReadStatusResponseDto(readStatus)));
        return readStatusResponseDtos;
    }

    private void validateUser(ReadStatusCreateDto readStatusCreateDto) {
        if (userRepository.findAll().stream()
                .noneMatch(user -> user.getId().equals(readStatusCreateDto.userId()))) throw new IllegalArgumentException("User not found");
    }

    private void validateChannel(ReadStatusCreateDto readStatusCreateDto) {
        if (channelRepository.findAll().stream()
                .noneMatch(channel -> channel.getId().equals(readStatusCreateDto.channelId()))) throw new IllegalArgumentException("Channel not found");
    }

    private void existsByUserAndChannel(ReadStatusCreateDto readStatusCreateDto){
        if (readStatusRepository.findAll().stream()
                .anyMatch(readStatus -> readStatus.getUserId().equals(readStatusCreateDto.userId())
                        && readStatus.getChannelId().equals(readStatusCreateDto.channelId()))) throw new IllegalStateException("User and Channel relationship already exists");
    }
}
