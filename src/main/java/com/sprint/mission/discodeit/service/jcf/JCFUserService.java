package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.Status;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

/********************************************
 * 유저 서비스 인터페이스 구현체
 * CRUD 실행
 * 2025.05.30 김민수
 ********************************************/
public class JCFUserService implements UserService {
    private MessageService jcfMessageService;

    private final List<User> data;

    public JCFUserService(MessageService jcfMessageService) {
        data = new ArrayList<>();
        this.jcfMessageService = jcfMessageService;
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

    /********************************************
     * 유저 정보 업데이트
     * @param userId  유저의 아이디 / 수정되지 않음 / 유저를 찾는 용도
     * @param userName  유저의 이름 / 수정될 수 있음
     * @param password  유저의 패스워드 / 수정될 수 잇음
     * @param status  유저의 상태 / enum 클래스 / (ONLINE,OFFLINE,AWAY,BUSY) 중 하나를 입력받음
     ********************************************/
    @Override
    public void updateUser(UUID userId, String userName, String password, Status status){
        Optional<User> tmp = data.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst();
        if(tmp.isPresent()) {
            tmp.get().setUserName(userName);
            tmp.get().setPassword(password);
            tmp.get().setStatus(status);
            tmp.get().setUpdatedAt(System.currentTimeMillis());
        }
    }
//    // 유저 내용 수정
//    @Override
//    public void updateUser(UUID userId, UpdateField updateField, String updatedText) {
//        Optional<User> user = data.stream()
//                .filter(u -> u.getId().equals(userId))
//                .findFirst();
//        if (user.isPresent()) {
//            switch(updateField) {
//                /********************************************
//                 * CASE TYPE_NAME : 유저의 이름 수정
//                 * CASE TYPE_PASSWORD : 패스워드 변경
//                 ********************************************/
//                case TYPE_NAME:
//                    user.get()
//                            .setUserName(updatedText);  // 유저의 이름 수정
//                    user.get()
//                            .setUpdatedAt(System.currentTimeMillis()); // 최종 업데이트 시간
//                    System.out.println("UserName 업데이트 성공");
//                    break;
//                case TYPE_PASSWORD :
//                    user.get()
//                            .setPassword(updatedText);
//                    user.get()
//                            .setUpdatedAt(System.currentTimeMillis());
//                    System.out.println("Password 변경 완료");
//            }
//        } else {
//            System.out.println("해당유저 없음");
//        }
//    }
//
//    @Override
//    public void updateUser(UUID userId, UpdateField updateField, Status updatedStatus) {
//        Optional<User> user = data.stream()
//                .filter(u -> u.getId().equals(userId))
//                .findFirst();
//        if (user.isPresent()) {
//            user.get().setStatus(updatedStatus);
//            user.get().setUpdatedAt(System.currentTimeMillis());
//            System.out.println("상태 변경 완료");
//        } else {
//            System.out.println("해당유저 없음");
//        }
//    }

    // 유저 삭제
    /********************************************
     *  유저 삭제 메서드 오버로딩
     *  deleteUser(User user) 유저객체로 삭제
     *  deleteUser(UUID userId) 유저 아이디로 객체를 식별 후 삭제
     ********************************************/

    @Override
    public void deleteUser(UUID userId) {
        Optional<User> us = data.stream().filter(u -> u.getId().equals(userId)).findFirst();
        if (us.isPresent()) {
            User user = us.get();
            data.remove(user);
            /********************************************
             * 유저가 있던 채널에서 유저 삭제
             ********************************************/
            List<Channel> userChannels = user.getChannels();
            for (Channel channel : userChannels){
                channel.getUsers().remove(user);
                channel.setUpdatedAt(System.currentTimeMillis());
            }
            // 모든 채널 내 해당 유저 삭제

            /********************************************
             * 유저가 작성한 메시지를 전체 메시지 내역에서 삭제
             ********************************************/
            List<Message> userMessages = user.getMessages();
            for (Message message : userMessages)
            jcfMessageService.removeMessage(message);
            // 해당 유저의 모든 대화 내역 삭제
        }
    }

    public void leaveChannel(User user, Channel channel) {
        if(data.contains(user)) {
            for (Message message : user.getMessages()) {
                if(message.getChannel().equals(channel)) {
                    jcfMessageService.removeMessage(message);
                }
            }
            user.getChannels().remove(channel);
            channel.removeUser(user);
        }
    }
}
