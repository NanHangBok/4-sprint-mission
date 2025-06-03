package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.factory.Factory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*********************************************
 *  채널 엔티티
 *  채널 객체의 정보 및 관리
 *  2025. 06. 02 김민수
 *********************************************/
public class Channel extends BasedEntity {

    private final UUID hostUserId;
    private String channelName;
    private final List<User> users;
    private final List<Message> messages;
    public Channel(User host, String channelName) {
        super();
        this.hostUserId = host.getId();
        this.channelName = channelName;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelId=" + super.getId() +
                ", channelCreatedAt=" + super.getCreatedAt() +
                ", channelUpdatedAt=" + super.getUpdatedAt() +
                ", hostUserId=" + hostUserId +
                ", channelName='" + channelName + '\'' +
                ", users=" + getUserNames() +  // 채널에 존재하는 유저의 이름 리스트
                ", messages=" + getMessageContents() +  // 채널에 존재하는 메시지의 내용 리스트
                '}';
    }

    /**
     * getter
     */
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
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * add, delete
     */
    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.addChannel(this);
        }
    }
    public void addMessage(Message message){
        if (!messages.contains(message)) {
            messages.add(message);
        }
    }

    public void removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            user.removeChannel(this);
        }
    }

    public void removeMessage(Message message) {
        if (messages.contains(message)) {
            messages.remove(message);
        }
    }

}
