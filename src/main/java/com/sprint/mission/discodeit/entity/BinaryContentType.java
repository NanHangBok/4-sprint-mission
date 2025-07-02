package com.sprint.mission.discodeit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum BinaryContentType {
    SVG("image/svg+xml"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    FILE("파일"),
    NONE("존재하지않음");

    private String value;
}
