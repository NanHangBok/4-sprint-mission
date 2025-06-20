package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class User extends BasedEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID profileId;
    private String name;
    private final List<Channel> channels = new ArrayList<>();
    private final List<Message> messages = new ArrayList<>();
    private String email;
    private String password;
    private PresenceStatus presenceStatus;

    public User(String name, String password, String email) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.presenceStatus = PresenceStatus.ONLINE;
    }

    public User(String name, String password, String email, UUID profileId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileId = profileId;
        this.presenceStatus = PresenceStatus.ONLINE;
    }
    /********************
     * 유저 업데이트를 위해 status를 추가한 생성자
     * 유저는 이름, 패스워드, 이메일, 상태를 변경할 수 있다.
     * @param name  유저의 이름
     * @param password  유저의 패스워드
     * @param presenceStatus  유저의 상태 / enum 클래스 사용
     * @param profileId  유저 프로필 이미지
     ********************/
    public User(String name, String password, PresenceStatus presenceStatus, UUID profileId) {
        super();
        this.name = name;
        this.password = password;
        this.presenceStatus = presenceStatus;
        this.profileId = profileId;
    }
    @Override
    public String toString() {
        return "User{" +
                "userId=" + getId() +
                ", userCreatedAt=" + getCreatedAt() +
                ", userUpdatedAt=" + getUpdatedAt() +
                ", userName='" + name + '\'' +
                ", email= " + email +
                ", password= " + password +
                ", channels=" + getChannelNames() +  // 유저가 존재하는 채널의 이름 리스트
                ", messages=" + getMessageContents() +  // 유저가 작성한 메시지의 내용 리스트
                ", status=" + presenceStatus.getValue() +  // status.getValue()로 상태 값을 변수명이 아닌 변수의 값을 받는다
                '}';
    }

    /**
     * getter
     */
    public List<String> getChannelNames() {
        return channels.stream().map(Channel::getChannelName)
                .toList();
    }

    public List<String> getMessageContents() {
        return messages.stream().map(Message::getContent)
                .toList();
    }

    /**
     * setter
     */

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPresenceStatus(PresenceStatus presenceStatus) {
        this.presenceStatus = presenceStatus;
    }

    public void setEmail(String email) { this.email = email; }

    public void setProfileId(UUID profileId) { this.profileId = profileId; }

    /**
     * add, delete
     */
    public void addChannel(Channel channel){
        if (channels.contains(channel)) return;
        channels.add(channel);
//        channel.addUser(this);
    }

    public void addMessage(Message message){
        if(messages.contains(message) && !message.getUserId().equals(getId())) return;
        messages.add(message);
        message.addUser(this);
    }

    public void removeChannel(Channel channel) {
        if(!channels.contains(channel)) return;
        channels.remove(channel);
//        channel.removeUser(this);
    }
    public void removeMessage(Message message){
        if(!messages.contains(message)) return;
        messages.remove(message);
        message.removeUser();
    }

    public void removeAllChannels(){
        channels.clear();
    }

    public void removeAllMessages(){
        messages.clear();
    }
}
