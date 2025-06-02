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

    private final List<User> data;
    
    private JCFUserService() {
        data = new ArrayList<>();
    }

    // 유저 생성
    @Override
    public User addUser(String name, String password, String email) {
        User user = new User(name, password, email);
        boolean emailMatch = data.stream().
                anyMatch(user1 -> user1.getEmail().equals(email));
<<<<<<< HEAD
        if (!emailMatch) {
            data.add(user);
            user.setActive(true);
=======
        if (emailMatch) {
            user.setActive(true);
            data.add(user);
>>>>>>> 663f3fd4b8842c33238ace0851f5e1d4a9cc374b
        }
        return user;
    }

    // 모든 유저 확인
    @Override
    public List<User> getUsers() {
        return data;
    }

    // 특정 ID를 가진 유저 가져오기
    @Override
    public Optional<User> getUsersById(UUID userId) {
        Optional<User> user = data.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();
        return user;
    }

    // 유저 내용 수정
    @Override
    public void updateUser(UUID userId, int select, String updatedText) {
        Optional<User> user = data.stream()
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

    // 유저 삭제
    @Override
    public void deleteUser(UUID userId) {
        Optional<User> us = data.stream().filter(u -> u.getUserId().equals(userId)).findFirst();

        if (us.isPresent()) {
            User user = us.get();
            data.remove(user);
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
<<<<<<< HEAD
                JCFMessageService.getMessageInstance().deleteMessage(message);
=======
                message.getUsers().remove(user);
>>>>>>> 663f3fd4b8842c33238ace0851f5e1d4a9cc374b
            }
            // 해당 유저의 모든 대화 내역 삭제
            user.setActive(false); //  상태 변경
        }

    }

}
