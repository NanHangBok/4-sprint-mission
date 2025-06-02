
package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {
    private final UUID messageId;
    private final Long messageCreatedAt;
    private Long messageUpdatedAt;

    private String content;
    private UUID userId;
    private UUID ChannelId;
<<<<<<< HEAD
=======
    private final List<User> users;
    private final List<Channel> channels;
>>>>>>> 663f3fd4b8842c33238ace0851f5e1d4a9cc374b
    private boolean isActive;

    public Message(String content, UUID userId, UUID channelId) {
        Long time = System.currentTimeMillis();
        this.messageId = UUID.randomUUID();
        this.messageCreatedAt = time;
        this.messageUpdatedAt = time;
        this.content = content;
        this.userId = userId;
        this.ChannelId = channelId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", messageCreatedAt=" + messageCreatedAt +
                ", messageUpdatedAt=" + messageUpdatedAt +
                ", content='" + content +
                ", userId=" + userId +
                ", ChannelId=" + ChannelId +
                '}';
    }

    public UUID getMessageId() {
        return messageId;
    }

    public Long getMessageCreatedAt() {
        return messageCreatedAt;
    }

    public Long getMessageUpdatedAt() {
        return messageUpdatedAt;
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

    public void setMessageUpdatedAt(Long messageUpdatedAt) {
        this.messageUpdatedAt = messageUpdatedAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
