package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.factory.Factory;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User extends BasedEntity{
    private enum Status {
        ONLINE,
        OFFLINE,
        AWAY,
        BUSY,
    }

    private String userName;
    private final List<Channel> channels;
    private final List<Message> messages;
    private String email;
    private String password;
    private Status status;
    public User(String userName, String password, String email) {
        super();
        this.userName = userName;
        this.channels = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.email = email;
        this.password = password;
        this.status = Status.ONLINE;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + getId() +
                ", userCreatedAt=" + getCreatedAt() +
                ", userUpdatedAt=" + getUpdatedAt() +
                ", userName='" + userName + '\'' +
                ", email= " + email +
                ", password= " + password +
                ", channels=" + getChannelNames() +  // 유저가 존재하는 채널의 이름 리스트
                ", messages=" + getMessageContents() +  // 유저가 작성한 메시지의 내용 리스트
                ", status=" + getStatus() +
                '}';
    }


    /**
     * getter
     */

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

    public Status getStatus() {
        return status;
    }

    /**
     * setter
     */

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(Status status) {
        this.status = status;
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
            UUID channelId = channel.getId();
            for (Message message : messages) {
                if (message.getChannelId().equals(channelId)) {
                    Factory.getInstance().getMessageService().deleteMessage(message);
                }
            }
            channels.remove(channel);
        }
    }
    public void deleteMessage(Message message){
        messages.remove(message);
    }
}
