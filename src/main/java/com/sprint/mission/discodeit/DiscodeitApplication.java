package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;

@SpringBootApplication
public class DiscodeitApplication {
	static User setupUser(UserService userService) {
		User user = userService.createUser("woody", "woody1234", "woody@codeit.com");
		return user;
	}

	static Channel setupChannel(ChannelService channelService, User user) {
		Channel channel = channelService.createChannel(user, "공지");
		return channel;
	}

	static User testUserCreate(UserService userService) {
		User user = userService.createUser("TEST","PASSWORD12","TEST@EMAIL.COM");
		return user;
	}

	static Channel testChannelCreate(ChannelService channelService, User user) {
		Channel channel = channelService.createChannel(user, "TEST");
		return channel;
	}

	static Message messageCreateTest(MessageService messageService, User author, Channel channel) {
		Message message = messageService.createMessage("안녕하세요.", author, channel);
		System.out.println("메시지 생성: " + message.getId());
		return message;
	}

	static User userFindTest(UserService userService, UUID userId){
		User user = userService.getUsersById(userId);
		System.out.println("유저 조회 : " + user.getId());
		return user;
	}

	static Channel channelFindTest(ChannelService channelService, UUID channelId) {
		Channel channel = channelService.find(channelId);
		System.out.println("채널 조회 : " + channel.getId());
		return channel;
	}

	static Message messageFindTest(MessageService messageService, UUID messageId) {
		Message message = messageService.getMessagesById(messageId);
		System.out.println("메시지 조회 : " + message.getId());
		return message;
	}

	static void userUpdateTest(UserService userService, UUID userId){
		User user = userService.getUsersById(userId);
		System.out.println("[수정 전 이름] : " + user.getName());
		User updateUser = new User ("수정된이름",null,null,null);
		userService.updateUser(user,updateUser);
		System.out.println("[수정 후 이름] : " + user.getName());
	}

	static void channelUpdateTest(ChannelService channelService, UUID channelId) {
		Channel channel = channelService.find(channelId);
		System.out.println("[수정 전 이름] : " + channel.getChannelName());
		channelService.updateChannel(channel,"수정된채널이름");
		System.out.println("[수정 후 이름] : " + channel.getChannelName());
	}

	static void messageUpdateTest(MessageService messageService, UUID messageId){
		Message message = messageService.getMessagesById(messageId);
		System.out.println("[수정 전 내용] : " + message.getContent());
		messageService.updateMessage(message);
		System.out.println("[수정 후 내용] : " + message.getContent());
	}

	static void userRemoveTest(UserService userService, UUID userId) {
		User user = userService.getUsersById(userId);
		System.out.println("삭제 전 조회 : " + userService.getUsersById(userId));
		userService.deleteUser(user);
		try {
			System.out.println("삭제 후 조회 : " + userService.getUsersById(userId));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void channelRemoveTest(ChannelService channelService, UUID channelId) {
		Channel channel = channelService.find(channelId);
		System.out.println("삭제 전 조회 : " + channelService.find(channelId));
		channelService.deleteChannel(channel.getId());
		try {
			System.out.println("삭제 후 조회 : " + channelService.find(channelId));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void messageRemoveTest(MessageService messageService, UUID messageId) {
		Message message = messageService.getMessagesById(messageId);
		System.out.println("삭제 전 조회 : " + messageService.getMessagesById(messageId));
		messageService.removeMessage(message);
		try {
			System.out.println("삭제 후 조회 : " + messageService.getMessagesById(messageId));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void Test(UserService userService, ChannelService channelService, MessageService messageService, UUID userId, UUID channelId, UUID messageId) {
		User findUser = userFindTest(userService,userId);
		System.out.println("찾은 유저 : " + findUser.getId());
		Channel findChannel = channelFindTest(channelService, channelId);
		System.out.println("찾은 채널 : " + findChannel.getId());
		Message findMessage = messageFindTest(messageService,messageId);
		System.out.println("찾은 메시지 : " + findMessage.getId());

		System.out.println("유저 업데이트 테스트");
		userUpdateTest(userService,userId);
		System.out.println("채널 업데이트 테스트");
		channelUpdateTest(channelService,channelId);
		System.out.println("메시지 업데이트 테스트");
		messageUpdateTest(messageService,messageId);

		System.out.println("메시지 삭제 테스트");
		messageRemoveTest(messageService,messageId);

		System.out.println("채널 삭제 테스트");
		channelRemoveTest(channelService,channelId);

		System.out.println("유저 삭제 테스트");
		userRemoveTest(userService,userId);

	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		UserService userService = context.getBean(UserService.class);
		ChannelService channelService = context.getBean(ChannelService.class);
		MessageService messageService = context.getBean(MessageService.class);

		System.out.println("[[[[[[[[[[[[[[[ 셋업 및 테스트 ]]]]]]]]]]]]]]]");
		// 셋업
		User setupUser = setupUser(userService);
		Channel setupChannel = setupChannel(channelService, setupUser);

		// 테스트
		Message setupMessage = messageCreateTest(messageService, setupUser, setupChannel);

		Test(userService,channelService,messageService,setupUser.getId(),setupChannel.getId(),setupMessage.getId());
		System.out.println("[[[[[[[[[[[[[[[ 셋업 및 테스트 ]]]]]]]]]]]]]]]");
	}

}
