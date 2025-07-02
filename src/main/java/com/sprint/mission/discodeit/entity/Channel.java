package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*********************************************
 *  채널 엔티티
 *  채널 객체의 정보 및 관리
 *  2025. 06. 02 김민수
 *********************************************/

@Getter
@Setter
@NoArgsConstructor
public class Channel extends BasedEntity {
    private UUID hostUserId;  // 채널 생성자(Host)의 UUID
    private UUID recipientId;
    private String channelName;
    private ChannelType channelType;
    private final List<UUID> userIds = new ArrayList<>();
    private final List<UUID> messageIds = new ArrayList<>();
    private String description;

    public Channel(UUID hostUserId, String channelName, String description) {
        super();
        this.hostUserId = hostUserId;
        this.channelName = channelName;
        this.description = description;
        this.channelType = ChannelType.PUBLIC;
    }

    public Channel(UUID user1, UUID user2) {
        this.hostUserId = user1;
        this.recipientId = user2;
        this.channelType = ChannelType.PRIVATE;
    }

    @Override
    public String toString() {
        if (channelType == ChannelType.PUBLIC) {
            return "Channel{" +
                    "ChannelType=" + channelType +
                    ", channelId=" + super.getId() +
                    ", channelCreatedAt=" + super.getCreatedAt() +
                    ", channelUpdatedAt=" + super.getUpdatedAt() +
                    ", hostUserId=" + hostUserId +
                    ", channelName='" + channelName + '\'' +
                    '}';
        } else if (channelType == ChannelType.PRIVATE) {
            return "Channel{" +
                    "ChannelType=" + channelType +
                    ", channelCreateAt=" + super.getCreatedAt() +
                    ", channelUsers=" + getUserIds() +
                    "}";
        }
        return "Invalid type provided";
    }

    public void addMessageToChannel(Message message) {
        if (this.messageIds.contains(message.getId())) throw new IllegalArgumentException("Message already exists");
        this.messageIds.add(message.getId());
        message.addChannel(this);
    }

    public void removeMessageFromChannel(Message message) {
        if (!this.messageIds.contains(message.getId())) throw new IllegalArgumentException("Message already removed");
        this.messageIds.remove(message.getId());
        message.removeChannel();
    }

    public void addUser(UUID userId) {
        if (this.userIds.contains(userId)) throw new IllegalArgumentException("User already exists");
        this.userIds.add(userId);
    }

    public void clearMessages() {
        this.messageIds.clear();
    }
}
