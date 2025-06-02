package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.service.jcf.Factory;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;

    private final UUID hostUserId;
    private String channelName;
    private final List<User> users;
    private final List<Message> messages;
    private boolean isActive;

    public Channel(User host, String channelName) {
        Long time = System.currentTimeMillis();
        this.id = UUID.randomUUID();
        this.createdAt = time;
        this.updatedAt = time;
        this.hostUserId = host.getId();
        this.channelName = channelName;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelId=" + id +
                ", channelCreatedAt=" + createdAt +
                ", channelUpdatedAt=" + updatedAt +
                ", hostUserId=" + hostUserId +
                ", channelName='" + channelName + '\'' +
                ", users=" + getUserNames() +  // 채널에 존재하는 유저의 이름 리스트
                ", messages=" + getMessageContents() +  // 채널에 존재하는 메시지의 내용 리스트
                '}';
    }

    /**
     * getter
     */
    public UUID getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
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
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    /**
     * add, delete
     */
    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.addChannel(this);
            setUpdatedAt(System.currentTimeMillis());
        }
    }
    public void addMessage(Message message){
        messages.add(message);
    }
    public void deleteUser(User user) {
        users.remove(user);
        user.deleteChannel(this);
    }
    public void deleteMessage(Message message) {
        Factory.getInstance()
                .getMessageService()
                .deleteMessage(message);
    }

}
