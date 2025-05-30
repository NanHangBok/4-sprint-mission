package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/********************************************
 * 채널 서비스 인터페이스 구현체
 * CRUD 실행
 * 채널 내 유저 추가 및 삭제
 * 2025.05.30 김민수
 ********************************************/
public class JCFChannelService implements ChannelService {
    private List<Channel> channels;

    public JCFChannelService() {
        this.channels = new ArrayList<>();
    }

    @Override
    public Channel addChannel(User user, String channelName) {
        Channel channel = new Channel(user, channelName);
        channels.add(channel);
        user.addChannels(channel);
        channel.updateUsers(user);
        return channel;
    }

    @Override
    public List<Channel> getChannels() {
        return channels;
    }

    @Override
    public Optional<Channel> getChannelById(UUID channelId) {
        return channels.stream()
                .filter(ch -> ch.getChannelId().equals(channelId))
                .findFirst();
    }

    @Override
    public void updateChannel(UUID channelId, int select, String updatedText) {
        Optional<Channel> tmp = channels.stream()
                .filter(ch -> ch.getChannelId().equals(channelId))
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
                            .setChannelUpdatedAt(System.currentTimeMillis()); // 채널 최종 업데이트 시간
                    System.out.println("채널명 수정 완료");
                    break;
            }
        }
    }

    @Override
    public void deleteChannel(Channel channel) {
        // 전체 채널 리스트에서 해당 채널 삭제
        channels.remove(channel);

        /***********************************
         * 채널을 가지고 있는 유저에게 채널 삭제
         ***********************************/
        List<User> users = channel.getUsers();
        for (User user : users) {
            user.getChannels().remove(channel);
        }
        // 모든 유저에게 해당 채널 삭제

        /***********************************
         * 전체 메시지 중 해당 채널의 메시지 삭제
         ***********************************/
        List<Message> messages = channel.getMessages();
        for (Message message : messages) {
            message.getChannels().remove(channel);
        }
        // 채널 내 모든 메세지 삭제
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
        List<User> targetUsers = channel.getUsers();
        Optional<User> user = targetUsers.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();
        user.ifPresentOrElse(
                u -> {targetUsers.remove(u);},
                     () -> System.out.println("해당유저가 채널에 없습니다."));
    }

    /***********************************
     * 해당 채널의 존재하는 특정 유저 삭제
     * @param channel 삭제할 유저가 있는 채널
     * @param user 삭제할 유저
     ***********************************/
    public void deleteChannelUser(Channel channel, User user) {
        if (channel.getUsers().contains(user)) {
            channel.getUsers().remove(user);
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
        List<User> targetUsers = channel.getUsers();
        targetUsers.add(user);  // 채널 사용자 추가
        user.addChannels(channel);  // 유저의 채널 리스트에 채널 추가
    }
}
