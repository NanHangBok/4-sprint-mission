package com.sprint.mission.discodeit.entity;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Channel {
    private final UUID channelId;
    private final Long channelCreatedAt;
    private Long channelUpdatedAt;

    private final UUID hostUserId;
    private String channelName;
    private final List<User> users;
    private final List<Message> messages;

    public Channel(User host, String channelName) {
        this.channelId = UUID.randomUUID();
        this.channelCreatedAt = System.currentTimeMillis();
        this.hostUserId = host.getUserId();
        this.channelName = channelName;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelId=" + channelId +
                ", channelCreatedAt=" + channelCreatedAt +
                ", channelUpdatedAt=" + channelUpdatedAt +
                ", hostUserId=" + hostUserId +
                ", channelName='" + channelName + '\'' +
                ", users=" + getUserNames() +  // 채널에 존재하는 유저의 이름 리스트
                ", messages=" + getMessageContents() +  // 채널에 존재하는 메시지의 내용 리스트
                '}';
    }

    /**
     * getter
     */
    public UUID getChannelId() {
        return channelId;
    }

    public Long getChannelCreatedAt() {
        return channelCreatedAt;
    }

    public Long getChannelUpdatedAt() {
        return channelUpdatedAt;
    }

    public UUID getHostUserId() {
        return hostUserId;
    }

    public String getChannelName() {
        return channelName;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<String> getUserNames() {
        return users.stream().map(User::getUserName)
                .toList();
    }

    public List<String> getMessageContents() {
        return messages.stream().map(Message::getContent)
                .toList();
    }
    /*********
     * setter
     *********/
    public void setChannelUpdatedAt(Long channelUpdatedAt) {
        this.channelUpdatedAt = channelUpdatedAt;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void updateUsers(User user) {
        users.add(user);
    }
    public void updateMessages(Message message){
        messages.add(message);
    }
}
