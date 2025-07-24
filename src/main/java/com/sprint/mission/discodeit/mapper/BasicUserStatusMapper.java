//package com.sprint.mission.discodeit.mapper;
//
//import com.sprint.mission.discodeit.dto.UserStatusPostDto;
//import com.sprint.mission.discodeit.dto.UserStatusDto;
//import com.sprint.mission.discodeit.entity.User;
//import com.sprint.mission.discodeit.entity.UserStatus;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BasicUserStatusMapper {
//    public UserStatus toUserStatus(UserStatusPostDto postDto, User user) {
//        return new UserStatus(postDto.userId(), postDto.latestActiveAt(), true, user);
//    }
//
//    public UserStatusDto toUserStatusDto(UserStatus userStatus) {
//        return new UserStatusDto(userStatus.getId(), userStatus.getUserId(), userStatus.getLastActiveAt());
//    }
//}
