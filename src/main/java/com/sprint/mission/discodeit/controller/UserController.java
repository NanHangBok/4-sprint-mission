package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserStatusService userStatusService;
    private final BinaryContentMapper binaryContentMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@ModelAttribute UserPostDto userPostDto ) {
        BinaryContentPostDto binaryContentPostDto = binaryContentMapper.ofBinaryContentPostDto(userPostDto.binaryContentType(), userPostDto.profile());
        UserCreateDto userCreateDto = userMapper.ofUserCreateDto(userPostDto, binaryContentPostDto);

        UserCreateResponseDto response = userService.createUser(userCreateDto);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{user-id}")
    public ResponseEntity update(@PathVariable("user-id") UUID userId, @RequestBody UserUpdateDto userUpdateDto) {
        UserResponseDto response = userService.updateUser(userId,userUpdateDto);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{user-id}")
    public ResponseEntity delete(@PathVariable("user-id") UUID userId) {
        userService.deleteUser(userId);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity findALl() {
        List<UserResponseDto> responses = userService.findAllUsers();

        return ResponseEntity.ok(responses);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{user-id}/user-status")
    public ResponseEntity findUserStatus(@PathVariable("user-id") UUID userId) {
        UserResponseDto userResponseDto = userService.findUserById(userId);
        UserStatusResponseDto response = userStatusService.updateByUserId(userId);

        return ResponseEntity.ok(response);
    }
}
