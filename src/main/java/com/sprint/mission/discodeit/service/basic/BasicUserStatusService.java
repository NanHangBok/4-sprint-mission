package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatusPostDto;
import com.sprint.mission.discodeit.dto.UserStatusUpdateDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    private void validateUser(UUID userId) {
        if (!userRepository.findAll().stream()
                .anyMatch(user -> user.equals(userId))) throw new NoSuchElementException("User not found");
    }

    private void existsUserStatus(UUID userId) {
        if (!userStatusRepository.findAll().stream()
                .anyMatch(userStatus -> userStatus.getUserId().equals(userId))) throw new IllegalArgumentException("User already exists.");
    }

    @Override
    public UserStatus create(UserStatusPostDto userStatusPostDto) {
        validateUser(userStatusPostDto.userId());
        existsUserStatus(userStatusPostDto.userId());

        UserStatus userStatus = new UserStatus(userStatusPostDto.userId(), userStatusPostDto.latestActiveAt());
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
    public void update(UserStatusUpdateDto userStatusUpdateDto) {
        UserStatus findUserStatus = userStatusRepository.findById(userStatusUpdateDto.id());

        if (findUserStatus.getLastActiveAt().isAfter(userStatusUpdateDto.latestActiveAt())) {
            System.out.println("저장되어있는 데이터가 동일하거나 더 최신입니다.");
            return;
        }
        Optional.ofNullable(userStatusUpdateDto.latestActiveAt()).ifPresent(findUserStatus::setLastActiveAt);
        findUserStatus.setUpdatedAt(userStatusUpdateDto.latestActiveAt());

        userStatusRepository.save(findUserStatus);
    }

    @Override
    public void updateByUserId(UUID userId, Instant latestActiveAt) {
        UserStatus findUserStatus = userStatusRepository.findByUserId(userId);
        if (findUserStatus.getLastActiveAt().isAfter(latestActiveAt)) {
            System.out.println("저장되어있는 데이터가 동일하거나 더 최신입니다.");
            return;
        }
        findUserStatus.setLastActiveAt(latestActiveAt);
        findUserStatus.setUpdatedAt(latestActiveAt);

        userStatusRepository.save(findUserStatus);
    }

    @Override
    public void delete(UUID id) {
        userStatusRepository.delete(id);
    }
}
