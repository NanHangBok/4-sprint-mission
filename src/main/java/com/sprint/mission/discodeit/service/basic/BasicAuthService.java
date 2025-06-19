package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.LoginUserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {
    private final UserRepository userRepository;

    @Override
    public User login(LoginUserDto loginUserDto) {
        if (userRepository.findAll()
                .stream()
                .anyMatch(user -> user.getName().equals(loginUserDto.username())
                                    && user.getPassword().equals(loginUserDto.password()))) {
            return userRepository.findByName(loginUserDto.username());
        } else throw new IllegalArgumentException();

    }
}
