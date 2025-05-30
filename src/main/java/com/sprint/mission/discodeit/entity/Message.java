package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Message {
    private final UUID messageId;
    private final Long messageCreatedAt;
    private Long messageUpdatedAt;

    private String content;
    private UUID userId;
    private UUID ChannelId;
    private final List<User> users;
    private final List<Channel> channels;

    public Message(String content, UUID userId, UUID channelId) {
        Long time = System.currentTimeMillis();
        this.messageId = UUID.randomUUID();
        this.messageCreatedAt = time;
        this.messageUpdatedAt = time;
        this.content = content;
        this.userId = userId;
        this.ChannelId = channelId;
        this.users = new ArrayList<>();
        this.channels = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", messageCreatedAt=" + messageCreatedAt +
                ", messageUpdatedAt=" + messageUpdatedAt +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", ChannelId=" + ChannelId +
                ", userNames=" + getUserNames() +  // 메시지를 작성한 유저 리스트
                ", challneNames=" + getChannelNames() +  // 메시지가 존재하는 채널 리스트
                '}';
    }

    /**
     * getter
     */
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

    public List<User> getUsers() {
        return users;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public List<String> getUserNames() {
        return users.stream().map(User::getUserName)
                .toList();
    }

    public List<String> getChannelNames() {
        return channels.stream().map(Channel::getChannelName)
                .toList();
    }
    /**
     * setter
     */
    public void setMessageUpdatedAt(Long messageUpdatedAt) {
        this.messageUpdatedAt = messageUpdatedAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void updateChannels(Channel channel){
        channels.add(channel);
    }

    public void updateUsers(User user){
        users.add(user);
    }
}
