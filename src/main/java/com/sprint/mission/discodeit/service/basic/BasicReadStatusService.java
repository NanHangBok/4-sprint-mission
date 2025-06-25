package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusPostDto;
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
    public ReadStatusResponseDto create(ReadStatusPostDto readStatusPostDto) {
        validateByUserOrChannel(readStatusPostDto);
        existsByUserAndChannel(readStatusPostDto);

        ReadStatus readStatus = new ReadStatus(readStatusPostDto.userId(), readStatusPostDto.channelId(), Instant.now());
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
    public ReadStatusResponseDto update(ReadStatusUpdateDto readStatusUpdateDto) {
        ReadStatus findReadStatus = readStatusRepository.findById(readStatusUpdateDto.id());

        Optional.ofNullable(readStatusUpdateDto.latestTime()).ifPresent(findReadStatus::setLatestTime);
        readStatusRepository.save(findReadStatus);
        return readStatusMapper.toReadStatusResponseDto(readStatusRepository.findById(readStatusUpdateDto.id()));
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.delete(id);
    }
    // 테스트용 findAll()
    public List<ReadStatusResponseDto> findAll() {
        List<ReadStatusResponseDto> readStatusResponseDtos = new ArrayList<>();
        readStatusRepository.findAll().stream()
                .forEach(readStatus -> readStatusResponseDtos.add(readStatusMapper.toReadStatusResponseDto(readStatus)));
        return readStatusResponseDtos;
    }

    private void validateByUserOrChannel(ReadStatusPostDto readStatusPostDto) {
        if (userRepository.findAll().stream()
                .noneMatch(user -> user.getId().equals(readStatusPostDto.userId()))) throw new NoSuchElementException("User not found");
        if (channelRepository.findAll().stream()
                .noneMatch(channel -> channel.getId().equals(readStatusPostDto.channelId()))) throw new NoSuchElementException("No such channel");
    }

    private void existsByUserAndChannel(ReadStatusPostDto readStatusPostDto){
        if (readStatusRepository.findAll().stream()
                .anyMatch(readStatus -> readStatus.getUserId().equals(readStatusPostDto.userId())
                        && readStatus.getChannelId().equals(readStatusPostDto.channelId()))) throw new NoSuchElementException("User and Channel relationship already exists");
    }
}
