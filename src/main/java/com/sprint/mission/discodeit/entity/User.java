package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;

    private String userName;
    private final List<Channel> channels;
    private final List<Message> messages;
    private String email;
    private String password;
    private boolean isActive;

    public User(String userName, String password, String email) {
        Long time = System.currentTimeMillis();
        this.id = UUID.randomUUID();
        this.createdAt = time;
        this.updatedAt = time;
        this.userName = userName;
        this.channels = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", userCreatedAt=" + createdAt +
                ", userUpdatedAt=" + updatedAt +
                ", userName='" + userName + '\'' +
                ", email= " + email +
                ", password= " + password +
                ", channels=" + getChannelNames() +  // 유저가 존재하는 채널의 이름 리스트
                ", messages=" + getMessageContents() +  // 유저가 작성한 메시지의 내용 리스트
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

    public String getUserName() {
        return userName;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<String> getChannelNames() {
        return channels.stream().map(Channel::getChannelName)
                .toList();
    }

    public List<String> getMessageContents() {
        return messages.stream().map(Message::getContent)
                .toList();
    }

    public String getEmail() {
        return email;
    }

    /**
     * setter
     */

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * add, delete
     */
    public void addChannel(Channel channel){
        if (!channels.contains(channel)) {
            channels.add(channel);
            channel.addUser(this);
        }
    }

    public void addMessage(Message message){
        if(!messages.contains(message) &&
                message.getUserId().equals(this.getId()))  {
            messages.add(message);}
    }

    public void deleteChannel(Channel channel){
        if (channels.contains(channel)) {
            channels.remove(channel);
            channel.deleteUser(this);
            UUID channelId = channel.getId();
            for (Message message : messages) {
                if (message.getChannelId().equals(channelId)) {
                    deleteMessage(message);
                }
            }
            setUpdatedAt(System.currentTimeMillis());
        }
    }
    public void deleteMessage(Message message){
        if (messages.contains(message)) {
            messages.remove(message);
            setUpdatedAt(System.currentTimeMillis());
        }
    }
}
