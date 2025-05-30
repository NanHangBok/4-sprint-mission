package com.sprint.mission.discodeit.run;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.util.Optional;
import java.util.UUID;

public class JavaApplication {
    public static void main(String[] args) {
        JCFUserService jcfUserService = new JCFUserService();
        JCFChannelService jcfChannelService = new JCFChannelService();
        JCFMessageService jcfMessageService = new JCFMessageService();

        System.out.println("------------- 1. USER ------------");
        System.out.println("---------등록---------");
        User u1 = jcfUserService.addUser("김코딩","password", "email1@abc.df");
        User u2 = jcfUserService.addUser("이코드", "password2", "abcd@email.com");
        User u3 = jcfUserService.addUser("박자바","pswd123", "java@email.com");
        System.out.println();

        System.out.println("---------다건조회---------");
        jcfUserService.getUsers().
                forEach(System.out::println);
        System.out.println();

        System.out.println("---------단건조회---------");
        UUID uid = u1.getUserId();
        Optional<User> targetUser = jcfUserService.getUsersById(uid);
        if (targetUser.isPresent()) {
            System.out.println(targetUser);
        } else {
            System.out.println("해당 유저 없음");
        }
        System.out.println();

        System.out.println("---------전체 조회 (수정 전)---------");
        jcfUserService.getUsers().
                forEach(System.out::println);
        System.out.println();

        System.out.println("---------전체 조회 (수정 후)---------");
        jcfUserService.updateUser(uid,1,"최객체");
        jcfUserService.updateUser(u2.getUserId(),2,"UnknownPW");
        jcfUserService.getUsers().
                forEach(System.out::println);
        System.out.println();

        System.out.println("---------전체 조회 (삭제 전)---------");
        jcfUserService.getUsers().
                forEach(System.out::println);
        jcfUserService.deleteUser(u1);
        System.out.println();

        System.out.println("---------전체 조회 (삭제 후)---------");
        jcfUserService.getUsers().
                forEach(System.out::println);
        System.out.println();

        /*********************************
         * 채널 테스트
         *********************************/

        System.out.println("------------------채널 테스트------------------");
        System.out.println("---------등록---------");
        Channel ch1 = jcfChannelService.addChannel(u1,"채널1");
        Channel ch2 = jcfChannelService.addChannel(u2,"채널2");
        Channel ch3 = jcfChannelService.addChannel(u1,"채널3");
        System.out.println();

        System.out.println("---------다건 조회---------");
        jcfChannelService.getChannels()
                .forEach(System.out::println);
        System.out.println();

        System.out.println("---------단건 조회---------");
        Optional<Channel> targetChannel = jcfChannelService.getChannelById(ch1.getChannelId());
        targetChannel.ifPresentOrElse(System.out::println, () -> System.out.println("해당 채널 없음"));
        System.out.println();

        System.out.println("---------전체 조회 (수정 전)---------");
        jcfChannelService.getChannels().
                forEach(System.out::println);
        System.out.println();

        System.out.println("---------전체 조회 (수정 후)---------");
        jcfChannelService.updateChannel(ch1.getChannelId(),1,"수정된 채널");
        jcfChannelService.getChannels()
                .forEach(System.out::println);
        System.out.println();

        System.out.println("---------전체 조회 (삭제 전)---------");
        jcfChannelService.getChannels()
                .forEach(System.out::println);
        System.out.println();

        System.out.println("---------전체 조회 (삭제 후)---------");
        jcfChannelService.deleteChannel(ch1);
        jcfChannelService.getChannels()
                .forEach(System.out::println);
        System.out.println();

        System.out.println("--------- 채널 내 유저 추가 ---------");
        jcfChannelService.getChannels().forEach(System.out::println);
        jcfChannelService.addChannelUser(ch1,u3);
        jcfChannelService.addChannelUser(ch2,u3);
        System.out.println("--------- 유저 추가 후 ---------");
        jcfChannelService.getChannels().forEach(System.out::println);
        System.out.println();

        System.out.println("--------- 채널 내 유저 삭제 ---------");
        jcfChannelService.getChannels().forEach(System.out::println);
        jcfChannelService.deleteChannelUser(ch2,u3.getUserId());
        jcfChannelService.deleteChannelUser(ch1,u3);
        System.out.println();

        /**********************************************
         * 메세지 테스트
         **********************************************/

        System.out.println("------------------메세지 테스트------------------");
        System.out.println("---------등록---------");
        Message msg1 = jcfMessageService.addMessage("내용1",u1,ch1);
        Message msg2 = jcfMessageService.addMessage("내용2",u2,ch1);
        Message msg3 = jcfMessageService.addMessage("내용3",u1,ch2);
        Message msg4 = jcfMessageService.addMessage("내용4",u3,ch2);
        Message msg5 = jcfMessageService.addMessage("내용5",u2,ch2);
        System.out.println();

        System.out.println("---------다건 조회---------");
        jcfMessageService.getMessages()
                .forEach(System.out::println);
        System.out.println();

        System.out.println("---------단건 조회---------");
        jcfMessageService.getMessagesById(msg1.getMessageId())
                .ifPresentOrElse(System.out::println,()-> System.out.println("해당 메시지 없음"));
        System.out.println();

        System.out.println("---------전체 조회 (수정 전)---------");
        jcfMessageService.getMessages().
                forEach(System.out::println);
        System.out.println();

        System.out.println("---------전체 조회 (수정 후)---------");
        jcfMessageService.updateMessage(msg1.getMessageId(),1,"수정된 내용입니다.");
        jcfMessageService.getMessages().
                forEach(System.out::println);
        System.out.println();

        System.out.println("---------전체 조회 (삭제 전)---------");
        jcfMessageService.getMessages()
                .forEach(System.out::println);
        System.out.println();

        System.out.println("---------전체 조회 (삭제 후)---------");
        jcfMessageService.deleteMessage(msg2);
        jcfMessageService.getMessages()
                .forEach(System.out::println);
        System.out.println();

        /***********************************************
         * 전체 조회
         ***********************************************/

        System.out.println("------------------전체 조회------------------");
        System.out.println("---------유저 조회---------");
        jcfUserService.getUsers()
                .forEach(System.out::println);

        System.out.println("---------채널 조회---------");
        jcfChannelService.getChannels()
                .forEach(System.out::println);

        System.out.println("---------메세지 조회---------");
        jcfMessageService.getMessages()
                .forEach(System.out::println);
        System.out.println();
        
        /***********************************
         * 유저 삭제 시 채널 및 메시지 삭제 확인
         ***********************************/
        System.out.println("------------------ 유저 삭제 시 채널 내 유저 삭제 확인 ------------------");
        
        User testUser1 = jcfUserService.addUser("유저1","testPW1","TEST@email.com");
        User testUser2 = jcfUserService.addUser("유저2","TESTpw2","testmail@email.com");
        User testUser3 = jcfUserService.addUser("유저3","testPSWD","email@pass.word");
        
        jcfChannelService.addChannelUser(ch3,testUser1);
        jcfChannelService.addChannelUser(ch3,testUser2);
        jcfChannelService.addChannelUser(ch3,testUser3);
        System.out.println(ch3.getUserNames());
        System.out.println("유저3 삭제");
        jcfUserService.deleteUser(testUser3);

        System.out.println(ch3.getUserNames());
    }
}