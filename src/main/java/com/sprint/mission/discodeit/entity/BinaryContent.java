package com.sprint.mission.discodeit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class BinaryContent {
    private UUID id;
    private Instant createdAt;
    private ActiveStatus active;

    private BinaryContentType contentType;
    private final byte[] content;

    public BinaryContent(byte[] content, BinaryContentType contentType) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.active = ActiveStatus.ACTIVE;
        this.content = content;
    }
}
