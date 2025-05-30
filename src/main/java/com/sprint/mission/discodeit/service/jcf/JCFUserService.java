package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import jdk.dynalink.beans.StaticClass;
import org.w3c.dom.ls.LSOutput;

import java.util.*;

/********************************************
 * 유저 서비스 인터페이스 구현체
 * CRUD 실행
 * 2025.05.30 김민수
 ********************************************/
public class JCFUserService implements UserService {
    private static final JCFUserService userInstance = new JCFUserService();

    public static JCFUserService getUserInstance() {
        return userInstance;
    }

    private final List<User> users;
    private JCFUserService() {
        users = new ArrayList<>();
    }

    @Override
    public User addUser(String name, String password, String email) {
        User user = new User(name, password, email);
        users.add(user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public Optional<User> getUsersById(UUID userId) {
        Optional<User> user = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();
        return user;
    }

    @Override
    public void updateUser(UUID userId, int select, String updatedText) {
        Optional<User> user = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();
        if (user.isPresent()) {
            switch(select) {
                /********************************************
                 * CASE 1 : 유저의 이름 수정
                 * CASE 2 : 패스워드 변경
                 ********************************************/
                case 1:
                    user.get()
                            .setUserName(updatedText);  // 유저의 이름 수정
                    user.get()
                            .setUserUpdatedAt(System.currentTimeMillis()); // 최종 업데이트 시간
                    System.out.println("UserName 업데이트 성공");
                    break;
                case 2:
                    user.get()
                            .setPassword(updatedText);
                    user.get()
                            .setUserUpdatedAt(System.currentTimeMillis());
                    System.out.println("Password 변경 완료");
            }
        } else {
            System.out.println("해당유저 없음");
        }
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);

        /********************************************
         * 유저가 있던 채널에서 유저 삭제
         ********************************************/
        List<Channel> userChannel = user.getChannels();
        for (Channel channel : userChannel){
            channel.getUsers().remove(user);
        }
        // 모든 채널 내 해당 유저 삭제

        /********************************************
         * 유저가 작성한 메시지를 전체 메시지 내역에서 삭제
         ********************************************/
        List<Message> userMessage = user.getMessages();
        for (Message message : userMessage){
            message.getUsers().remove(user);
        }
        // 해당 유저의 모든 대화 내역 삭제
    }

}
