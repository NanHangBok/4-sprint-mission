package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/***********************************
 * 메세지 서비스 인터페이스 구현체
 * CRUD 실행
 * 2025.05.30 김민수
 ***********************************/
public class JCFMessageService implements MessageService {
    private static final JCFMessageService messageInstance = new JCFMessageService();
    JCFUserService jcfUserService = JCFUserService.getUserInstance();
    JCFChannelService jcfChannelService = JCFChannelService.getChannelInstance();
    public static JCFMessageService getMessageInstance() {
        return messageInstance;
    }

    private final List<Message> data;

    private JCFMessageService() {
        this.data = new ArrayList<>();
    }


    // 메시지 생성
    @Override
    public Message addMessage(String content, UUID userId, UUID channelId) {
        Optional<User> optUser = jcfUserService.getUsersById(userId);
        Optional<Channel> optChannel = jcfChannelService.getChannelById(channelId);
        Message message = new Message(content,userId,channelId);
        if (optUser.isPresent() && optChannel.isPresent()) {
            User user = optUser.get();
            Channel channel = optChannel.get();

<<<<<<< HEAD
            data.add(message);
            message.setActive(true);
            channel.updateMessages(message);
            user.addMessages(message);
=======
            message.setActive(true);
            data.add(message);
            channel.updateMessages(message);
            user.addMessages(message);
            message.updateChannels(channel);
            message.updateUsers(user);
>>>>>>> 663f3fd4b8842c33238ace0851f5e1d4a9cc374b
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
                .filter(msg -> msg.getMessageId().equals(messageId))
                .findFirst();
    }

    // 메세지 내용 수정
    @Override
    public void updateMessage(UUID messageId, int select, String updatedText) {
        Optional<Message> tmp = data.stream()
                .filter(msg -> msg.getMessageId().equals(messageId))
                .findFirst();
        if (tmp.isPresent()) {
            switch (select) {
                /******************************
                 *  CASE 1 : 내용 수정
                 *  CASE 2 이후 추가
                 ******************************/
                case 1:
                    tmp.get()
                            .setContent(updatedText);  // 내용 수정
                    tmp.get()
                            .setMessageUpdatedAt(System.currentTimeMillis());  // 최종 업데이트 시간
                    System.out.println("메시지 내용 수정 완료");
                    break;
            }
        }
    }

    // 메시지 삭제
    @Override
    public void deleteMessage(Message message) {
        data.remove(message);

        /********************************************
         * 메시지가 있는 채널 내 메시지 삭제
         ********************************************/
        for (Channel channel : jcfChannelService.getChannels()) {
            channel.getMessages().remove(message);
        }
        // 채널 내 메시지 삭제

        /********************************************
         * 메시지를 작성한 유저의 내역에 메시지 삭제
         ********************************************/
        for (User user : jcfUserService.getUsers()) {
            user.getMessages().remove(message);
        }
        message.setActive(false);
    }

}
