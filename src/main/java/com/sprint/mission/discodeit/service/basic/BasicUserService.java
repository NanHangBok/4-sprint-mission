package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.exception.BusinessLogicException;
import com.sprint.mission.discodeit.exception.ExceptionCode;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentStorage binaryContentStorage;

    @Transactional(rollbackFor = BusinessLogicException.class)
    @Override
    public User createUser(UserCreateRequest userCreateRequest, UUID binaryContentId) {
        isDuplicateEmail(userCreateRequest.email());
        isDuplicateName(userCreateRequest.username());

        BinaryContent profile = null;
        if (binaryContentId == null) {
            System.out.println("이미지가 포함되지 않아 기본 프로필로 설정됩니다.");
        } else {
            profile = binaryContentRepository.findById(binaryContentId).orElse(null);
        }
        User user = new User(userCreateRequest.username(), userCreateRequest.password(), userCreateRequest.email(), profile);
        userRepository.save(user);

        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(rollbackFor = BusinessLogicException.class)
    @Override
    public User updateUser(UUID userId, UserUpdateRequest userUpdateRequest, UUID binaryContentId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("<UNK> <UNK> <UNK> <UNK> <UNK> <UNK>."));

        isDuplicateName(userUpdateRequest.newUsername());
        isDuplicateEmail(userUpdateRequest.newEmail());

        Optional.ofNullable(userUpdateRequest.newUsername())
                .ifPresent(findUser::setUsername);
        Optional.ofNullable(userUpdateRequest.newPassword())
                .ifPresent(findUser::setPassword);
        Optional.ofNullable(userUpdateRequest.newEmail())
                .ifPresent(findUser::setEmail);
        Optional.ofNullable(binaryContentId).ifPresent(bcId -> {
            Optional.ofNullable(findUser.getProfile()).ifPresent(binaryContentRepository::delete);
            BinaryContent binaryContent = binaryContentRepository.findById(bcId).orElse(null);
            binaryContentRepository.save(binaryContent);
            findUser.setProfile(binaryContent);
        });
        Instant now = Instant.now();
        UserStatus userStatus = userStatusRepository.findByUserId(findUser.getId());
        userStatus.update(now);
        userRepository.save(findUser);
        userStatusRepository.save(userStatus);

        return findUser;
    }

    @Transactional(rollbackFor = BusinessLogicException.class)
    @Override
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        validateActiveUser(user);

        // 프로필 이미지가 있는 경우에만 제거한다.
        if (user.getProfileId() != null) {
            binaryContentRepository.deleteById(user.getProfileId());
            user.setProfile(null);
        }

        userRepository.delete(user);

        userStatusRepository.deleteByUserId(user.getId());
    }

    private void validateActiveUser(User user) {
        if (!userRepository.existsById(user.getId())) throw new BusinessLogicException(ExceptionCode.USER_NOT_FOUND);
    }

    // 동일한 이메일이 존재하는지 확인
    private void isDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new BusinessLogicException(ExceptionCode.EMAIL_OR_USERNAME_ALREADY_EXISTS);
    }

    private void isDuplicateName(String name) {
        if (userRepository.existsByUsername(name))
            throw new BusinessLogicException(ExceptionCode.EMAIL_OR_USERNAME_ALREADY_EXISTS);

    }
}
