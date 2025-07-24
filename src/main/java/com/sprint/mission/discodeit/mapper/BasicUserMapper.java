//package com.sprint.mission.discodeit.mapper;
//
//import com.sprint.mission.discodeit.dto.*;
//import com.sprint.mission.discodeit.entity.User;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Component
//public class BasicUserMapper {
//    public User ofUser(UserCreateRequest userCreateRequest, UUID profileId) {
//        return new User(userCreateRequest.username(), userCreateRequest.password(), userCreateRequest.email(), profileId);
//    }
//
//    public UserResponseDto ofUserResponseDto(User user, UserStatusDto userStatusDto) {
//        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getProfileId(), userStatusDto);
//    }
//
//    public UserLoginResponseDto toUserLoginResponseDto(User user) {
//        return new UserLoginResponseDto(user.getId(), user.getProfileId(), user.getUsername(), user.getEmail());
//    }
//
//    public UserCreateResponseDto ofUserCreateResponseDto(User user, UserStatusDto userStatusDto) {
//        return new UserCreateResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getProfileId(), userStatusDto);
//    }
//
/// /    public UserDto toUserDto(User user) {
/// /        return new UserDto(user.getId(), user.getUsername(), user.getEmail(),
/// /                user.getProfileId(), user.getPresenceStatus().equals(PresenceStatus.ONLINE));
/// /    }
//
//    public UserDto ofUserDto(User user, BinaryContentDto binaryContentDto, boolean online) {
//        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), binaryContentDto, online);
//    }
//
//}
