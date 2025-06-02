package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

/********************************************
 * 유저 서비스 인터페이스 구현체
 * CRUD 실행
 * 2025.05.30 김민수
 ********************************************/
public class JCFUserService implements UserService {

    private final List<User> data;
    
    JCFUserService() {
        data = new ArrayList<>();
    }

    // 유저 생성
    @Override
    public User createUser(String name, String password, String email) {
        User user = new User(name, password, email);
        boolean emailMatch = data.stream().
                anyMatch(user1 -> user1.getEmail().equals(email));
        if (!emailMatch) {
            data.add(user);
            user.setActive(true);
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
                .filter(u -> u.getId().equals(userId))
                .findFirst();
        return user;
    }

    // 유저 내용 수정
    @Override
    public void updateUser(UUID userId, int select, String updatedText) {
        Optional<User> user = data.stream()
                .filter(u -> u.getId().equals(userId))
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
                            .setUpdatedAt(System.currentTimeMillis()); // 최종 업데이트 시간
                    System.out.println("UserName 업데이트 성공");
                    break;
                case 2:
                    user.get()
                            .setPassword(updatedText);
                    user.get()
                            .setUpdatedAt(System.currentTimeMillis());
                    System.out.println("Password 변경 완료");
            }
        } else {
            System.out.println("해당유저 없음");
        }
    }

    // 유저 삭제
    /********************************************
     *  유저 삭제 메서드 오버로딩
     *  deleteUser(User user) 유저객체로 삭제
     *  deleteUser(UUID userId) 유저 아이디로 객체를 식별 후 삭제
     ********************************************/

    /********************************************
     * 유저 삭제
     * @param user 삭제할 유저
     ********************************************/
    @Override
    public void deleteUser(User user) {
        data.remove(user);
        /********************************************
         * 유저가 있던 채널에서 유저 삭제
         ********************************************/
        List<Channel> userChannels = user.getChannels();
        for (Channel channel : userChannels){
            channel.getUsers().remove(user);
        }
        // 모든 채널 내 해당 유저 삭제

        /********************************************
         * 유저가 작성한 메시지를 전체 메시지 내역에서 삭제
         ********************************************/
        List<Message> userMessages = user.getMessages();
        for (Message message : userMessages){
            Factory.getInstance().getMessageService().deleteMessage(message);
        }
        // 해당 유저의 모든 대화 내역 삭제
        user.setActive(false); //  상태 변경
    }

    @Override
    public void deleteUser(UUID userId) {
        Optional<User> us = data.stream().filter(u -> u.getId().equals(userId)).findFirst();
        if (us.isPresent()) {
            User user = us.get();
            this.deleteUser(user);
        }

    }

}
