
package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
public class Message extends BasedEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String content;
    private UUID userId;
    private UUID channelId;
    private List<UUID> attachmentIds;

    public Message(UUID userId, UUID channelId, String content) {
        super();
        this.content = content;
        this.userId = this.userId;
        this.channelId = this.channelId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + getId() +
                ", messageCreatedAt=" + getCreatedAt() +
                ", messageUpdatedAt=" + getUpdatedAt() +
                ", content='" + content +
                "', userId=" + userId +
                ", ChannelId=" + channelId +
                '}';
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // 작성한 유저
    public void addUser(User user) {
        if (this.userId == null){
            this.userId = user.getId();
            user.addMessage(this);
        }
    }

    // 작성된 채널
    public void addChannel(Channel channel) {
        if (this.channelId == null){
            this.channelId = channel.getId();
            channel.addMessage(this);
        }
    }

    // 유저가 삭제하거나 유저가 삭제된 경우
    public void removeUser() {
        if (this.userId != null) {
            this.userId = null;
        }
    }

    // 채널에서 삭제된 경우
    public void removeChannel() {
        if (this.channelId != null) {
            this.channelId = null;
        }
    }
}
