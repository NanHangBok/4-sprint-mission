
package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends BasedEntity {
    private String content;
    private UUID userId;
    private UUID ChannelId;

    public Message(String content, UUID userId, UUID channelId) {
        super();
        this.content = content;
        this.userId = userId;
        this.ChannelId = channelId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + getId() +
                ", messageCreatedAt=" + getCreatedAt() +
                ", messageUpdatedAt=" + getUpdatedAt() +
                ", content='" + content +
                ", userId=" + userId +
                ", ChannelId=" + ChannelId +
                '}';
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

    public void setContent(String content) {
        this.content = content;
    }

}
