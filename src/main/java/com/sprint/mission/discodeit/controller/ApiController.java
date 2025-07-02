package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ApiController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final BinaryContentService binaryContentService;
    private final BinaryContentMapper binaryContentMapper;

    @RequestMapping("/")
    public String index() {
        return "user-list.html";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/api/user/findAll")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> userDtos = userService.findAll();
        return ResponseEntity.ok(userDtos);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/api/binaryContent/find")
    public ResponseEntity<BinaryContent> findBinaryContent(@RequestParam UUID binaryContentId) {
        BinaryContent binaryContent = binaryContentService.findBinaryContent(binaryContentId);
        return ResponseEntity.ok(binaryContent);
    }
}
