package com.sprint.mission.discodeit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ChannelType {
    PUBLIC("공개"),
    PRIVATE("비공개");

    private String value;

}
