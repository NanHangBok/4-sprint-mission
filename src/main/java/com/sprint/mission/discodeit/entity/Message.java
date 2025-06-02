
package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;

    private String content;
    private UUID userId;
    private UUID ChannelId;
    private boolean isActive;

    public Message(String content, UUID userId, UUID channelId) {
        Long time = System.currentTimeMillis();
        this.id = UUID.randomUUID();
        this.createdAt = time;
        this.updatedAt = time;
        this.content = content;
        this.userId = userId;
        this.ChannelId = channelId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + id +
                ", messageCreatedAt=" + createdAt +
                ", messageUpdatedAt=" + updatedAt +
                ", content='" + content +
                ", userId=" + userId +
                ", ChannelId=" + ChannelId +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getContent() {
        return content;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getChannelId() {
        return ChannelId;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
