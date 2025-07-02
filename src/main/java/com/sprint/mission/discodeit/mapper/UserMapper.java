package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.PresenceStatus;
import com.sprint.mission.discodeit.entity.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {
    public User ofUser(UserCreateDto userCreateDto, UUID profileId) {
        return new User(userCreateDto.name(), userCreateDto.nickname(), userCreateDto.password(), userCreateDto.email(), profileId);
    }

    public UserResponseDto ofUserResponseDto(User user, UserStatusResponseDto userStatusResponseDto) {
        return new UserResponseDto(user.getId(), user.getNickname(), user.getEmail(), user.getProfileId(), userStatusResponseDto);
    }

    public UserLoginResponseDto toUserLoginResponseDto(User user) {
        return new UserLoginResponseDto(user.getId(), user.getProfileId(), user.getNickname(), user.getEmail(), user.getPresenceStatus());
    }

    public UserCreateResponseDto ofUserCreateResponseDto(User user, UserStatusResponseDto userStatusResponseDto) {
        return new UserCreateResponseDto(user.getId(),user.getName(),user.getNickname(),user.getEmail(),user.getProfileId(),userStatusResponseDto);
    }

    public UserCreateDto ofUserCreateDto(UserPostDto userPostDto, BinaryContentPostDto binaryContentPostDto) {
        return new UserCreateDto(userPostDto.name(), userPostDto.nickname(), userPostDto.password(), userPostDto.email(), binaryContentPostDto);
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getCreatedAt(), user.getUpdatedAt(),
                user.getName(), user.getEmail(), user.getProfileId(), user.getPresenceStatus().equals(PresenceStatus.ONLINE));
    }
}
