package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.factory.Factory;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/***********************************
 * 메세지 서비스 인터페이스 구현체
 * CRUD 실행
 * 2025.05.30 김민수
 ***********************************/
public class JCFMessageService implements MessageService {
    UserService jcfUserService;
    ChannelService jcfChannelService;

    private final List<Message> data;

    public JCFMessageService(UserService jcfUserService, ChannelService jcfChannelService) {
        this.data = new ArrayList<>();
        this.jcfUserService = jcfUserService;
        this.jcfChannelService = jcfChannelService;
    }


    // 메시지 생성
    @Override
    public Message createMessage(String content, UUID userId, UUID channelId) {
        Optional<User> optUser = jcfUserService.getUsersById(userId);
        Optional<Channel> optChannel = jcfChannelService.getChannelById(channelId);
        Message message = new Message(content,userId,channelId);
        if (optUser.isPresent() && optChannel.isPresent()) {
            User user = optUser.get();
            Channel channel = optChannel.get();
            data.add(message);
            channel.addMessage(message);
            user.addMessage(message);
            return message;
        } else {
            return message;
        }
    }


    // 모든 메시지 확인
    @Override
    public List<Message> getMessages() {
        return data;
    }

    // 특정 ID를 가진 메시지 확인
    @Override
    public Optional<Message> getMessagesById(UUID messageId) {
        return data.stream()
                .filter(msg -> msg.getId().equals(messageId))
                .findFirst();
    }

    @Override
    public void updateMessage(UUID messageId, String content) {
        Optional<Message> tmp = data.stream()
                .filter(msg -> msg.getId().equals(messageId))
                .findFirst();
        if (tmp.isPresent()) {
            tmp.get().setContent(content);
            tmp.get().setUpdatedAt(System.currentTimeMillis());
        }
    }
//    // 메세지 내용 수정
//    @Override
//    public void updateMessage(UUID messageId, UpdateField updateField, String updatedText) {
//        Optional<Message> tmp = data.stream()
//                .filter(msg -> msg.getId().equals(messageId))
//                .findFirst();
//        if (tmp.isPresent()) {
//            switch (updateField) {
//                /******************************
//                 *  CASE TYPE_CONTENT : 내용 수정
//                 *  CASE 2 이후 추가
//                 ******************************/
//                case TYPE_CONTENT :
//                    tmp.get()
//                            .setContent(updatedText);  // 내용 수정
//                    tmp.get()
//                            .setUpdatedAt(System.currentTimeMillis());  // 최종 업데이트 시간
//                    System.out.println("메시지 내용 수정 완료");
//                    break;
//            }
//        }
//    }

    // 메시지 삭제
    @Override
    public void removeMessages(List<Message> messages) {
        for (Message message : messages){
            if (data.contains(message)) {
                data.remove(message);
                jcfChannelService.getChannels().stream()
                        .filter(ch -> ch.getId().equals(message.getId()))
                        .forEach(ch -> ch.removeMessage(message));
                jcfUserService.getUsers().stream()
                        .filter(user -> user.getId().equals(message.getId()))
                        .forEach(user -> user.removeMessage(message));
            }
        }
    }

    public void removeMessage(Message message) {
        if(data.contains(message)){
            data.remove(message);
            jcfUserService.removeMessage(message.getUserId(),message);
            jcfChannelService.removeMessage(message.getChannelId(),message);
        }
    }
}
