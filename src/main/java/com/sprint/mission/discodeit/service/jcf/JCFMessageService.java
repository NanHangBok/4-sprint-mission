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
    private List<Message> messages;

    public JCFMessageService() {
        this.messages = new ArrayList<>();
    }

    @Override
    public Message addMessage(String content, User user, Channel channel) {
        Message message = new Message(content,user.getUserId(),channel.getChannelId());
        messages.add(message);
        channel.updateMessages(message);
        user.addMessages(message);
        message.updateChannels(channel);
        message.updateUsers(user);
        return message;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public Optional<Message> getMessagesById(UUID messageId) {
        return messages.stream()
                .filter(msg -> msg.getMessageId().equals(messageId))
                .findFirst();
    }

    @Override
    public void updateMessage(UUID messageId, int select, String updatedText) {
        Optional<Message> tmp = messages.stream()
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

    @Override
    public void deleteMessage(Message message) {
        messages.remove(message);

        /********************************************
         * 메시지가 있는 채널 내 메시지 삭제
         ********************************************/
        List<Channel> channels = message.getChannels();
        for (Channel channel : channels) {
            channel.getMessages().remove(message);
        }
        // 채널 내 메시지 삭제

        /********************************************
         * 메시지를 작성한 유저의 내역에 메시지 삭제
         ********************************************/
        List<User> users = message.getUsers();
        for (User user : users) {
            user.getMessages().remove(message);
        }
    }
}
