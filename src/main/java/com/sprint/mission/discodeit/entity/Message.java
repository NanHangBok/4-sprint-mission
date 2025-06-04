
package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends BasedEntity {
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
                ", userId=" + user.getId() +
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

    public void setContent(String content) {
        this.content = content;
    }

    public void addUser(User user) {
        if (this.user == null){
            this.user = user;
            user.addMessage(this);
        }
    }
    public void addChannel(Channel channel) {
        if (this.channel == null){
            this.channel = channel;
            channel.addMessage(this);
        }
    }
    public void removeUser(User user) {
        if (this.user != null) {
            this.user = null;
            user.removeMessage(this);
        }
    }
    public void removeChannel(Channel channel) {
        if (this.channel != null) {
            this.channel = null;
            channel.removeMessage(this);
        }
    }
}
