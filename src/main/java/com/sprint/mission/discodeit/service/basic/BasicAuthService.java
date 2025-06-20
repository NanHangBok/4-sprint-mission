package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserLoginDto;
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
    public User login(UserLoginDto userLoginDto) {
        if (userRepository.findAll()
                .stream()
                .anyMatch(user -> user.getName().equals(userLoginDto.username())
                                    && user.getPassword().equals(userLoginDto.password()))) {
            return userRepository.findByName(userLoginDto.username());
        } else throw new IllegalArgumentException();

    }
}
