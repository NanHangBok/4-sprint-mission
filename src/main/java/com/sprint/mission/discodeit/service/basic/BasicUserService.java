package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentMapper binaryContentMapper;
    private final UserMapper userMapper;

    public void validateActiveUser(User user) {
        if (!user.getActive().equals(ActiveStatus.ACTIVE)) throw new IllegalArgumentException("User is not active");
    }

    // 동일한 이메일이 존재하는지 확인
    private void isDuplicateEmail(String email) {
        if (userRepository.findAll().stream()
                .noneMatch(user -> user.getEmail().equals(email))) throw new IllegalArgumentException("Email already exists");
    }

    private void isDuplicateName(String name) {
        if (userRepository.findAll().stream()
                .noneMatch(user -> user.getName().equals(name))) throw new IllegalArgumentException("Username already exists");
    }

    @Override
    public User createUser(UserPostDto userPostDto) {
        isDuplicateEmail(userPostDto.email());
        isDuplicateName(userPostDto.name());

        UUID profileId = null;
        if (userPostDto.binaryContentPostDto() != null) {
            BinaryContent binaryContent = binaryContentMapper.toBinaryContent(userPostDto.binaryContentPostDto());
            profileId = binaryContent.getId();

            binaryContent.setActive(ActiveStatus.ACTIVE);
            binaryContentRepository.save(binaryContent);
        }

        if (profileId == null) System.out.println("이미지가 포함되지 않아 기본 프로필로 설정됩니다.");

        User user =  userMapper.toUser(userPostDto,profileId);
        user.setActive(ActiveStatus.ACTIVE);
        userRepository.save(user);

        UserStatus userStatus = new UserStatus(user.getId(),Instant.now());
        userStatusRepository.save(userStatus);
        return user;
    }

    // 모든 유저 확인
    @Override
    public List<UserResponseDto> getUsers() {
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        userRepository.findAll()
                .forEach(user -> {
                    UserStatus userStatus = userStatusRepository.findByUserId(user.getId());
                    userResponseDtos.add(userMapper.toUserResponseDto(user,userStatus));
                });
//        return userRepository.findAll();
        return userResponseDtos;
    }

    // 특정 ID를 가진 유저 가져오기
    @Override
    public UserResponseDto getUserById(UUID userId) {
        User user = userRepository.findById(userId);
        UserStatus userStatus = userStatusRepository.findByUserId(user.getId());
        UserResponseDto userResponseDto = userMapper.toUserResponseDto(user,userStatus);
        return userResponseDto;
    }

    @Override
    public UserResponseDto getUserByName(String name) {
        User user = userRepository.findByName(name);
        UserStatus userStatus = userStatusRepository.findById(user.getId());
        UserResponseDto userResponseDto = userMapper.toUserResponseDto(user,userStatus);
        return userResponseDto;
    }

    @Override
    public void updateUser(UserUpdateDto userUpdateDto) {
        User findUser = userRepository.findById(userUpdateDto.id());

        Optional.ofNullable(userUpdateDto.nickname())
                .ifPresent(findUser::setNickname);
        Optional.ofNullable(userUpdateDto.password())
                .ifPresent(findUser::setPassword);
        Optional.ofNullable(userUpdateDto.presenceStatus())
                .ifPresent(findUser::setPresenceStatus);
        Optional.ofNullable(userUpdateDto.binaryContentPostDto())
                .ifPresent((dto) -> {
                    BinaryContent binaryContent = binaryContentMapper.toBinaryContent(dto);
                    Optional.ofNullable(findUser.getProfileId()).ifPresent(binaryContentRepository::delete);
                    binaryContent.setActive(ActiveStatus.ACTIVE);
                    binaryContentRepository.save(binaryContent);
                    findUser.setProfileId(binaryContent.getId());
                });
        Instant now = Instant.now();
        findUser.setUpdatedAt(now);
        UserStatus userStatus = userStatusRepository.findByUserId(findUser.getId());
        userStatus.update(now);
        userRepository.save(findUser);
        userStatusRepository.save(userStatus);
    }

    @Override
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId);
        validateActiveUser(user);

        // 프로필 이미지가 있는 경우에만 제거한다.
        if (user.getProfileId() != null) {
            binaryContentRepository.delete(user.getProfileId());
            user.setProfileId(null);
        }

        // 유저가 가진 채널과 정보 모두 삭제
        user.setActive(ActiveStatus.DELETE);
        userRepository.delete(user);

        userStatusRepository.delete(user.getId());
    }
}
