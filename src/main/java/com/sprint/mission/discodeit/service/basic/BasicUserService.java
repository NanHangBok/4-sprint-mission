package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.UUID;

public class BasicUserService implements UserService {
    UserRepository userRepository;
    ChannelRepository channelRepository;
    MessageRepository messageRepository;

    public BasicUserService(UserRepository userRepository, ChannelRepository channelRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
        this.messageRepository = messageRepository;
    }

    public void validateActiveUsre(User user) {
        if (!user.isActive().equals(ActiveStatus.ACTIVE)) throw new IllegalArgumentException("User is not active");
    }

    // 동일한 이메일이 존재하는지 확인
    @Override
    public boolean isDuplicateEmail(String email) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    // 유저 생성
    @Override
    public User createUser(String name, String password, String email) {
        User user = new User(name, password, email);
        if (isDuplicateEmail(email)) throw new IllegalArgumentException("Email already exists");

        user.setActive(ActiveStatus.ACTIVE);
        userRepository.save(user);

        return user;
    }

    // 모든 유저 확인
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // 특정 ID를 가진 유저 가져오기
    @Override
    public User getUsersById(UUID userId) {
        User user = userRepository.findById(userId);
        return user;
    }

    /********************************************
     * 유저 정보 업데이트
     * @param user  유저의 아이디 / 수정되지 않음 / 유저를 찾는 용도
     * @param updateUser 수정할 정보를 담은 User / null이 아닌 값을 기존 유저의 값을 수정
     ********************************************/
    @Override
    public void updateUser(User user, User updateUser) {
        User target = userRepository.findById(user.getId());
        validateActiveUsre(target);

        boolean isUpdated = false;

        if (updateUser.getUserName() != null) {
            user.setUserName(updateUser.getUserName());
            isUpdated = true;
        }
        if (updateUser.getPassword() != null) {
            user.setPassword(updateUser.getPassword());
            isUpdated = true;
        }
        if (updateUser.getEmail() != null) {
            user.setEmail(updateUser.getEmail());
            isUpdated = true;
        }
        if (updateUser.getStatus() != null) {
            user.setStatus(updateUser.getStatus());
            isUpdated = true;
        }

        if (isUpdated) {
            user.setUpdatedAt(System.currentTimeMillis());
            userRepository.save(user);
        }
    }

    /********************************************
     *  유저 삭제 메서드
     * @param user 아이디를 입력으로 받음
     ********************************************/
    @Override
    public void deleteUser(User user) {
        validateActiveUsre(user);

        /********************************************
         * 유저가 있던 채널에서 유저 삭제
         ********************************************/
        user.getChannels().stream()
                .forEach(channel -> {
                    channel.getUsers().remove(user);
                    channel.setUpdatedAt(System.currentTimeMillis());
                    channelRepository.save(channel);
                });
        // 모든 채널 내 해당 유저 삭제

        /********************************************
         * 유저가 작성한 메시지를 전체 메시지 내역에서 삭제
         ********************************************/
        List<Message> messages = user.getMessages();
        for (Message message : messages) {
            removeMessage(user,message);
        }

        // 유저가 가진 채널과 정보 모두 삭제
        user.removeAllChannels();
        user.removeAllMessages();
        user.setActive(ActiveStatus.DELETE);
        userRepository.delete(user);
    }

    /**
     * 채널 나가기
     * 유저가 채널을 나가서 삭제되거나 채널이 유저를 퇴장해서 채널이 삭제될 때 사용
     * @param user
     * @param channel
     */
    public void leaveChannel(User user, Channel channel) {
        validateActiveUsre(user);

        user.getMessages().stream()
                .filter(message -> message.getChannelId().equals(channel.getId()))
                .forEach(message -> {
                    removeMessage(user,message);
                });
        user.getChannels().remove(channel);
        userRepository.save(user);
        channel.removeUser(user);
        channelRepository.save(channel);
    }

    @Override
    public void removeMessage(User user, Message message) {
        if (!user.getMessages().contains(message)) throw new IllegalArgumentException("Message does not exist");
        Channel channel = message.getChannel();
        channel.removeMessage(message);
        channelRepository.save(channel);
        message.removeUser();
        message.removeChannel();
        message.setUpdatedAt(System.currentTimeMillis());
        message.setActive(ActiveStatus.DELETE);
        messageRepository.delete(message);
    }
}
