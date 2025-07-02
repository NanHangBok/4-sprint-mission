package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserLoginDto;
import com.sprint.mission.discodeit.dto.UserLoginResponseDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Instant;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserLoginResponseDto login(UserLoginDto userLoginDto) {
        if (userLoginDto.password() == null || userLoginDto.username() == null) throw new IllegalArgumentException("Username or Password is null");
        User finduser = userRepository.findAll().stream()
                .filter(user -> user.getName().equals(userLoginDto.username())
                && user.getPassword().equals(userLoginDto.password()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Username or Password is invalid"));
        finduser.setUpdatedAt(Instant.now());
        return userMapper.toUserLoginResponseDto(finduser);
    }
}
