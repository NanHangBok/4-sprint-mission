package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*********************************************
 *  채널 엔티티
 *  채널 객체의 정보 및 관리
 *  2025. 06. 02 김민수
 *********************************************/

@Getter
@Setter
@NoArgsConstructor
public class Channel extends BasedEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID hostUserId;  // 채널 생성자(Host)의 UUID
    private String channelName;
    private ChannelType channelType;
    private final List<UUID> userIds = new ArrayList<>();
    private final List<UUID> messageIds = new ArrayList<>();
    private String description;

    public Channel(UUID hostUserId, String channelName, String description) {
        super();
        this.hostUserId = hostUserId;
        this.channelName = channelName;
        this.description = description;
        this.channelType = ChannelType.PUBLIC;
    }

    public Channel(UUID user1, UUID user2) {
        this.userIds.add(user1);
        this.userIds.add(user2);
        this.channelType = ChannelType.PRIVATE;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelId=" + super.getId() +
                ", channelCreatedAt=" + super.getCreatedAt() +
                ", channelUpdatedAt=" + super.getUpdatedAt() +
                ", hostUserId=" + hostUserId +
                ", channelName='" + channelName + '\'' +
//                ", users=" + getUserNames() +  // 채널에 존재하는 유저의 이름 리스트
//                ", messages=" + getMessageContents() +  // 채널에 존재하는 메시지의 내용 리스트
                '}';
    }

    /**
     * getter
     */

//    public List<String> getUserNames() {
//        return users.stream().map(User::getName)
//                .toList();
//    }
//
//    public List<String> getMessageContents() {
//        return messages.stream().map(Message::getContent)
//                .toList();
//    }

    /********************
     * add, delete
     ********************/

//    // 채널에 유저 추가
//    public void addUser(User user) {
//        if (userIds.contains(user.getId())) {
//            System.out.println("해당 유저는 이미 존재합니다.");
//            return;
//        }
//        userIds.add(user.getId());
//        user.addChannel(this);
//    }

    // 채널에 메시지 추가 ( 채널에서 발생한 메시지 )
    public void addMessage(Message message){
        if (messageIds.contains(message.getId())) {
            System.out.println("해당 메시지는 이미 존재합니다.");
            return;
        }
        messageIds.add(message.getId());
        message.addChannel(this);
    }

//    // 채널에서 유저 삭제 ( 유저 퇴장 )
//    public void removeUser(User user-> UUID userId) {
//        if (!userIds.contains(user)) {
//            System.out.println("해당 유저는 이미 삭제되었습니다.");
//            return;
//        }
//        userIds.remove(user);
//        user.removeChannel(this);
//    }

    // 채널에서 메시지 삭제
    public void removeMessage(UUID messageId) {
        if (!messageIds.contains(messageId)) {
            System.out.println("해당 메시지는 이미 삭제되었습니다.");
            return;
        }
        messageIds.remove(messageId);
    }

}
