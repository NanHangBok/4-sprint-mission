package com.sprint.mission.discodeit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum BinaryContentType {
    PROFILE("유저 프로필"),
    IMAGE("이미지"),
    FILE("파일");

    private String value;
}
