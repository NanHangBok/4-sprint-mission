package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.CreateProfileUserDto;
import com.sprint.mission.discodeit.dto.ReadUserDto;
import com.sprint.mission.discodeit.dto.UpdateUserDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final UserStatusRepository userStatusRepository;

    public void validateActiveUsre(User user) {
        if (!user.getActive().equals(ActiveStatus.ACTIVE)) throw new IllegalArgumentException("User is not active");
    }

    // 동일한 이메일이 존재하는지 확인
    @Override
    public boolean isDuplicateEmail(String email) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    public boolean isDuplicateName(String name) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getName().equals(name));
    }
    // 유저 생성
//    @Override
//    public User createUser(String name, String password, String email) {
//        if (isDuplicateEmail(email) || isDuplicateName(name)) throw new IllegalArgumentException("Email or Username already exists");
//        User user = new User(name, password, email);
//        CreateProfileUserDto profileUser = new CreateProfileUserDto(name, password, email, null);
//
//        user.setActive(ActiveStatus.ACTIVE);
//        userRepository.save(user);
//
//        return user;
//    }
    @Override
    public User createUser(CreateProfileUserDto profileUserDto) {
        if (isDuplicateEmail(profileUserDto.email())
                || isDuplicateName(profileUserDto.name())) throw new IllegalArgumentException("Email or Username already exists");
        if (profileUserDto.binaryContent() == null) {
            System.out.println("이미지가 포함되지 않아 기본 프로필로 설정됩니다.");
            User user =  new User(profileUserDto.name(), profileUserDto.password(), profileUserDto.email());
            user.setActive(ActiveStatus.ACTIVE);
            userRepository.save(user);

            UserStatus userStatus = new UserStatus(user.getId(),Instant.now());
            userStatusRepository.save(userStatus);
            return user;
        }
        BinaryContent binaryContent = new BinaryContent(profileUserDto.binaryContent());
        User user = new User(profileUserDto.name(), profileUserDto.password(), profileUserDto.email(), binaryContent.getId());

        binaryContent.setActive(ActiveStatus.ACTIVE);
        binaryContent.setUserId(user.getId());
        binaryContentRepository.save(binaryContent);

        user.setActive(ActiveStatus.ACTIVE);
        userRepository.save(user);

        UserStatus userStatus = new UserStatus(user.getId(),Instant.now());
        userStatusRepository.save(userStatus);
        return user;
    }

    // 모든 유저 확인
    @Override
    public List<ReadUserDto> getUsers() {
        List<ReadUserDto> readUserDtos = new ArrayList<>();
        userRepository.findAll()
                .forEach(user -> readUserDtos.add(new ReadUserDto(user.getName(),user.getEmail(),user.getProfileId(), userStatusRepository.findById(user.getId()))));
//        return userRepository.findAll();
        return readUserDtos;
    }

    // 특정 ID를 가진 유저 가져오기
    @Override
    public ReadUserDto getUsersById(UUID userId) {
        User user = userRepository.findById(userId);
        ReadUserDto readUserDto = new ReadUserDto(user.getName(),user.getEmail(),user.getProfileId(),userStatusRepository.findById(userId));
        return readUserDto;
    }

    @Override
    public void updateUser(UpdateUserDto updateUserDto) {

        User user = userRepository.findById(updateUserDto.id());
        validateActiveUsre(user);

        boolean isUpdated = false;
        if (updateUserDto.name() != null) {
            user.setName(updateUserDto.name());
            isUpdated = true;
        }
        if (updateUserDto.password() != null) {
            user.setPassword(updateUserDto.password());
            isUpdated = true;
        }
        if (updateUserDto.presenceStatus() != null) {
            user.setPresenceStatus(updateUserDto.presenceStatus());
            isUpdated = true;
        }
        if (updateUserDto.binaryContent() != null) {
            BinaryContent binaryContent = new BinaryContent(updateUserDto.binaryContent());
            user.setProfileId(binaryContent.getId());
            binaryContent.setActive(ActiveStatus.ACTIVE);
            binaryContent.setUserId(user.getId());
            binaryContentRepository.save(binaryContent);
            isUpdated = true;
        }
        if (isUpdated) {
            user.setUpdatedAt(Instant.now());
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

        // 유저가 가진 채널과 정보 모두 삭제
        user.removeAllChannels();
        user.removeAllMessages();
        user.setActive(ActiveStatus.DELETE);
        userRepository.delete(user);

        binaryContentRepository.delete(user.getId());
        userStatusRepository.delete(user.getId());
//        /********************************************
//         * 유저가 있던 채널에서 유저 삭제
//         ********************************************/
//        user.getChannels().stream()
//                .forEach(channel -> {
//                    channel.getUsers().remove(user);
//                    channel.setUpdatedAt(Instant.now());
//                    channelRepository.save(channel);
//                });
//        // 모든 채널 내 해당 유저 삭제
//
//        /********************************************
//         * 유저가 작성한 메시지를 전체 메시지 내역에서 삭제
//         ********************************************/
//        List<Message> messages = user.getMessages();
//        for (Message message : messages) {
//            removeMessage(user,message);
//        }

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
        Channel channel = message.getChannelId();
        channel.removeMessage(message);
        channelRepository.save(channel);
        message.removeUser();
        message.removeChannel();
        message.setUpdatedAt(Instant.now());
        message.setActive(ActiveStatus.DELETE);
        messageRepository.delete(message);
    }
}
