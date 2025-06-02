package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
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

    private final List<Message> data;

    JCFMessageService() {
        this.data = new ArrayList<>();
    }


    // 메시지 생성
    @Override
    public Message createMessage(String content, UUID userId, UUID channelId) {
        Optional<User> optUser = Factory.getInstance().getUserService().getUsersById(userId);
        Optional<Channel> optChannel = Factory.getInstance().getChannelService().getChannelById(channelId);
        Message message = new Message(content,userId,channelId);
        if (optUser.isPresent() && optChannel.isPresent()) {
            User user = optUser.get();
            Channel channel = optChannel.get();
            data.add(message);
            message.setActive(true);
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

    // 메세지 내용 수정
    @Override
    public void updateMessage(UUID messageId, int select, String updatedText) {
        Optional<Message> tmp = data.stream()
                .filter(msg -> msg.getId().equals(messageId))
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
                            .setUpdatedAt(System.currentTimeMillis());  // 최종 업데이트 시간
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
        for (Channel channel : Factory.getInstance().getChannelService().getChannels()) {
            channel.getMessages().remove(message);
        }
        // 채널 내 메시지 삭제

        /********************************************
         * 메시지를 작성한 유저의 내역에 메시지 삭제
         ********************************************/
        for (User user : Factory.getInstance().getUserService().getUsers()) {
            user.getMessages().remove(message);
        }
        message.setActive(false);
    }

}
