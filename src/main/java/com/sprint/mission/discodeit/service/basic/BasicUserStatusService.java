package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.CreateUserStatusDto;
import com.sprint.mission.discodeit.dto.UpdateUserStatusDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    public void validateUser(UUID userId) {
        if (!userRepository.findAll().stream()
                .anyMatch(user -> user.equals(userId))) throw new NoSuchElementException("User not found");
    }

    public void existsUser(UUID userId) {
        if (!userStatusRepository.findAll().stream()
                .anyMatch(userStatus -> userStatus.getUserId().equals(userId))) throw new IllegalArgumentException("User already exists.");
    }
    @Override
    public UserStatus create(CreateUserStatusDto createUserStatusDto) {
        validateUser(createUserStatusDto.userId());
        existsUser(createUserStatusDto.userId());

        UserStatus userStatus = new UserStatus(createUserStatusDto.userId(), createUserStatusDto.latestActiveAt());
        userStatusRepository.save(userStatus);

        return null;
    }

    @Override
    public UserStatus find(UUID id) {
        return userStatusRepository.findById(id);
    }

    @Override
    public List<UserStatus> findAll() {
        return userStatusRepository.findAll();
    }

    @Override
    public void update(UpdateUserStatusDto updateUserStatusDto) {
        UUID userId = updateUserStatusDto.id();
        Instant latestActiveAt = updateUserStatusDto.latestActiveAt();
        updateByUserId(userId,latestActiveAt);
    }

    @Override
    public void updateByUserId(UUID userId, Instant latestActiveAt) {
        UserStatus userStatus = userStatusRepository.findById(userId);

        userStatus.setLastActiveAt(latestActiveAt);
        userStatus.setUpdatedAt(Instant.now());
        userStatusRepository.save(userStatus);
    }

    @Override
    public void delete(UUID id) {
        userStatusRepository.delete(id);
    }
}
