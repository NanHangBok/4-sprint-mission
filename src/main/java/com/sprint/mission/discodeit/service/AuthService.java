package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserLoginDto;
import com.sprint.mission.discodeit.dto.UserLoginResponseDto;
import com.sprint.mission.discodeit.entity.User;


public interface AuthService {
    UserLoginResponseDto login(UserLoginDto userLoginDto);

}
