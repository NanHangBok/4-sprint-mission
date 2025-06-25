package com.sprint.mission.discodeit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;

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
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "BinaryContent{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", active=" + active +
                ", contentType=" + contentType +
                ", content=" + Arrays.toString(content) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BinaryContent that = (BinaryContent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
