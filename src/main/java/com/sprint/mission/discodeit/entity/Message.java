
package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Message extends BasedEntity{
    private String content;
    private UUID userId;
    private UUID channelId;
    private List<UUID> attachmentIds = new ArrayList<>();

    public Message(UUID userId, UUID channelId, String content) {
        super();
        this.content = content;
        this.userId = userId;
        this.channelId = channelId;
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
        if (this.userId != null) {
            System.out.println("유저 등록 완료");
            return;
        }
        this.userId = user.getId();
    }

    // 작성된 채널
    public void addChannel(Channel channel) {
        if (this.channelId != null) {
            System.out.println("채널 등록 완료");
            return;
        }
        this.channelId = channel.getId();
        channel.addMessageToChannel(this);
    }

    // 유저가 삭제하거나 유저가 삭제된 경우
    public void removeUser() {
        if (this.userId == null) {
            System.out.println("유저 삭제 완료");
            return;
        }
        this.userId = null;
    }

    // 채널에서 삭제된 경우
    public void removeChannel() {
        if (this.channelId == null) {
            System.out.println("채널 삭제 완료");
            return;
        }
        this.channelId = null;
    }
}
