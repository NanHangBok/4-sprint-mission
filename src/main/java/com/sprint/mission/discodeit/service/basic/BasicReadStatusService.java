package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusPostDto;
import com.sprint.mission.discodeit.dto.ReadStatusUpdateDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public boolean validateByUserOrChannel(ReadStatusPostDto readStatusPostDto) {
        if (userRepository.findAll().stream()
                .noneMatch(user -> user.equals(readStatusPostDto.userId()))) return false;
        if (channelRepository.findAll().stream()
                .noneMatch(channel -> channel.equals(readStatusPostDto.channelId()))) return false;
        return true;
    }

    public boolean existsByUserAndChannel(ReadStatusPostDto readStatusPostDto){
        if (readStatusRepository.findAll().stream()
                .anyMatch(readStatus -> readStatus.getUserId().equals(readStatusPostDto.userId())
                        && readStatus.getChannelId().equals(readStatusPostDto.channelId())))
            return false;

        return true;
    }
    @Override
    public ReadStatus create(ReadStatusPostDto readStatusPostDto) {
        if (!validateByUserOrChannel(readStatusPostDto)) throw new NoSuchElementException("User or Channel not found");

        if (existsByUserAndChannel(readStatusPostDto)) throw new IllegalArgumentException("User and Channel relationship already exists");

        ReadStatus readStatus = new ReadStatus(readStatusPostDto.userId(), readStatusPostDto.channelId(), Instant.now());
        readStatusRepository.save(readStatus);
        return null;
    }

    @Override
    public ReadStatus find(UUID id) {
        return readStatusRepository.findById(id);
    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        List<ReadStatus> list = readStatusRepository.findAll().stream()
                .filter(readStatus -> readStatus.getUserId().equals(userId))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public void update(ReadStatusUpdateDto readStatusUpdateDto) {
        ReadStatus readStatus = readStatusRepository.findById(readStatusUpdateDto.id());

        boolean isUpdated = false;

        if (readStatusUpdateDto.latestTime() != null) {
            readStatus.setUpdatedAt(readStatusUpdateDto.latestTime());
            isUpdated = true;
        }

        if (isUpdated) {
            readStatusRepository.save(readStatus);
        }
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.delete(id);
    }
}
