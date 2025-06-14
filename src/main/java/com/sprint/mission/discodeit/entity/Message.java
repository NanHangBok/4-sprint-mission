
package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Message extends BasedEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String content;
    private User user;
    private Channel channel;

    public Message(String content, User user, Channel channel) {
        super();
        this.content = content;
        this.user = user;
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + getId() +
                ", messageCreatedAt=" + getCreatedAt() +
                ", messageUpdatedAt=" + getUpdatedAt() +
                ", content='" + content +
                "', userId=" + user.getId() +
                ", ChannelId=" + channel.getId() +
                '}';
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public Channel getChannel() {
        return channel;
    }

    public UUID getUserId() {
        return user.getId();
    }

    public UUID getChannelId() {
        return channel.getId();
    }

    public void setContent(String content) {
        this.content = content;
    }

    // 작성한 유저
    public void addUser(User user) {
        if (this.user == null){
            this.user = user;
            user.addMessage(this);
        }
    }

    // 작성된 채널
    public void addChannel(Channel channel) {
        if (this.channel == null){
            this.channel = channel;
            channel.addMessage(this);
        }
    }

    // 유저가 삭제하거나 유저가 삭제된 경우
    public void removeUser() {
        if (this.user != null) {
            this.user = null;
        }
    }

    // 채널에서 삭제된 경우
    public void removeChannel() {
        if (this.channel != null) {
            this.channel = null;
        }
    }
}
