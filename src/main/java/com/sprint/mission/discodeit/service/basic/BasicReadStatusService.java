package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.CreateReadStatusDto;
import com.sprint.mission.discodeit.dto.UpdateReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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

    public boolean validateByUserOrChannel(CreateReadStatusDto createReadStatusDto) {
        if (userRepository.findAll().stream()
                .noneMatch(user -> user.equals(createReadStatusDto.userId()))) return false;
        if (channelRepository.findAll().stream()
                .noneMatch(channel -> channel.equals(createReadStatusDto.channelId()))) return false;
        return true;
    }

    public boolean existsByUserAndChannel(CreateReadStatusDto createReadStatusDto){
        if (readStatusRepository.findAll().stream()
                .anyMatch(readStatus -> readStatus.getUserId().equals(createReadStatusDto.userId())
                        && readStatus.getChannelId().equals(createReadStatusDto.channelId())))
            return false;

        return true;
    }
    @Override
    public ReadStatus create(CreateReadStatusDto createReadStatusDto) {
        if (!validateByUserOrChannel(createReadStatusDto)) throw new NoSuchElementException("User or Channel not found");

        if (existsByUserAndChannel(createReadStatusDto)) throw new IllegalArgumentException("User and Channel relationship already exists");

        ReadStatus readStatus = new ReadStatus(createReadStatusDto.userId(), createReadStatusDto.channelId(), Instant.now());
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
    public void update(UpdateReadStatusDto updateReadStatusDto) {
        ReadStatus readStatus = readStatusRepository.findById(updateReadStatusDto.id());

        boolean isUpdated = false;

        if (updateReadStatusDto.latestTime() != null) {
            readStatus.setUpdatedAt(updateReadStatusDto.latestTime());
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
