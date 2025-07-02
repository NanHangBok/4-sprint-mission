package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Instant createdAt;
    private ActiveStatus active;

    private BinaryContentType contentTypeEnum;
    private String contentType;
    private final byte[] bytes;

    public BinaryContent(MultipartFile bytes, BinaryContentType contentType) {
        try {
            this.id = UUID.randomUUID();
            this.createdAt = Instant.now();
            this.active = ActiveStatus.ACTIVE;
            this.bytes = bytes.getBytes();
            this.contentTypeEnum = contentType;
            this.contentType = contentType.getValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "BinaryContent{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", active=" + active +
                ", contentType=" + contentType +
                ", content=" + Arrays.toString(bytes) +
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
