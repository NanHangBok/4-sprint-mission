package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.FactoryService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/********************************************
 * 채널 서비스 인터페이스 구현체
 * CRUD 실행
 * 채널 내 유저 추가 및 삭제
 * 2025.05.30 김민수
 * *
 * 코드 리팩토링 2025. 06. 02 김민수
 ********************************************/
public class JCFChannelService implements ChannelService {
    private final List<Channel> data;

    JCFChannelService() {
        this.data = new ArrayList<>();
    }

    // 채널 생성
    @Override
    public Channel createChannel(User user, String channelName) {
        Channel channel = new Channel(user, channelName);
        if (hasUsers(user)) {
            addChannelUser(channel, user);
            data.add(channel);
            channel.setActive(true);
        }
        return channel;
    }

    // 현재 존재하는 모든 채널 확인
    @Override
    public List<Channel> getChannels() {
        return data;
    }


    // 채널의 ID를 사용하여 채널 검색
    @Override
    public Optional<Channel> getChannelById(UUID channelId) {
        return data.stream()
                .filter(ch -> ch.getId().equals(channelId))
                .findFirst();
    }
    // 채널 내용 수정
    @Override
    public void updateChannel(UUID channelId, int select, String updatedText) {
        Optional<Channel> tmp = data.stream()
                .filter(ch -> ch.getId().equals(channelId))
                .findFirst();
        if (tmp.isPresent()) {
            switch (select) {
                /***************
                 *  CASE 1 : 채널명 수정
                 *  CASE 2 이하 추가
                 ***************/
                case 1:
                    tmp.get()
                            .setChannelName(updatedText); // 채널명 수정
                    tmp.get()
                            .setUpdatedAt(System.currentTimeMillis()); // 채널 최종 업데이트 시간
                    System.out.println("채널명 수정 완료");
                    break;
            }
        }
    }
    // 채널 삭제
    @Override
    public void deleteChannel(Channel channel) {
        if (data.contains(channel)) {
            data.remove(channel);  // 전체 채널 리스트에서 해당 채널 삭제

            /***********************************
             * 채널을 가지고 있는 유저에게 채널 삭제
             ***********************************/
            for (User user : channel.getUsers()) {
                user.deleteChannel(channel);
            }  // 모든 유저에게 해당 채널 삭제

            /***********************************
             * 전체 메시지 중 해당 채널의 메시지 삭제
             ***********************************/
            for (Message message : channel.getMessages()) {
                Factory.getInstance().getMessageService().deleteMessage(message);
            }  // 채널 내 모든 메세지 삭제
        }
    }


    /***********************************
     * deleteChannelUser 오버로딩
     * deleteChannelUser(Channel, UUID) = UID로 채널 내 유저 삭제
     * deleteChannelUser(Channel, User) = 채널 내 유저 삭제
     ***********************************/

    /***********************************
     * 해당 채널의 존재하는 특정 유저를 id를 통해 삭제
     * @param channel 삭제할 유저가 있는 채널
     * @param userId 삭제할 유저의 id
     ***********************************/
    @Override
    public void deleteChannelUser(Channel channel, UUID userId) {
        Optional<User> target = Factory.getInstance()
                                        .getUserService()
                                        .getUsersById(userId);
        target.ifPresentOrElse(
                channel::deleteUser,
                () -> System.out.println("해당유저가 채널에 없습니다."));
    }

    /***********************************
     * 해당 채널의 존재하는 특정 유저 삭제
     * @param channel 삭제할 유저가 있는 채널
     * @param user 삭제할 유저
     ***********************************/

    @Override
    public void deleteChannelUser(Channel channel, User user) {
        if (channel.getUsers().contains(user)) {
            channel.deleteUser(user);
        } else {
            System.out.println("해당유저가 채널에 없습니다.");
        }
    }

    /***********************************
     * 해당 채널의 새로운 유저 추가
     * @param channel 유저를 추가할 채널
     * @param user 채널에 추가할 유저
     ***********************************/
    @Override
    public void addChannelUser(Channel channel, User user) {
        if (hasUsers(user)) {
            channel.addUser(user);
        }
    }

    /***********************************
     * 특정 유저가 전체 유저 리스트에 존재하는지 확인하는 로직
     * @param user 리스트에 존재하는지 확인하고 싶은 User 객체
     * @return 존재하면 true, 존재하지 않는다면 false 반환
     ***********************************/
    public boolean hasUsers(User user) {
        List<User> users = Factory.getInstance().getUserService().getUsers();
        return users.contains(user);
    }

}
