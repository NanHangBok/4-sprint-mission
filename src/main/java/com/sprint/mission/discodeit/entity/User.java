package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final UUID userId;
    private final Long userCreatedAt;
    private Long userUpdatedAt;

    private String userName;
    private final List<Channel> channels;
    private final List<Message> messages;
    private String email;
    private String password;

    public User(String userName, String password, String email) {
        Long time = System.currentTimeMillis();
        this.userId = UUID.randomUUID();
        this.userCreatedAt = time;
        this.userUpdatedAt = time;
        this.userName = userName;
        this.channels = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userCreatedAt=" + userCreatedAt +
                ", userUpdatedAt=" + userUpdatedAt +
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

    public UUID getUserId() {
        return userId;
    }

    public Long getUserCreatedAt() {
        return userCreatedAt;
    }

    public Long getUserUpdatedAt() {
        return userUpdatedAt;
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

    public void setUserUpdatedAt(Long userUpdatedAt) {
        this.userUpdatedAt = userUpdatedAt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addChannels(Channel channel){
        channels.add(channel);
    }

    public void addMessages(Message message){
        messages.add(message);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
