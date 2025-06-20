package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
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
		UserPostDto cuDto = new UserPostDto("woody","woody1234","woody@codeit.com",null);
		User user = userService.createUser(cuDto);
		return user;
	}

	static Channel setupChannel(ChannelService channelService, User user) {
		ChannelPostDto ccDto = new ChannelPostDto(user.getId(),"공지","공징입니다.");
		Channel channel = channelService.createPublicChannel(ccDto);
		return channel;
	}

	static User testUserCreate(UserService userService) {
		UserPostDto testUser = new UserPostDto("TEST","PASSWORD12","TEST@EMAIL.COM",null);
		User user = userService.createUser(testUser);
		return user;
	}

	static Channel testChannelCreate(ChannelService channelService, User user) {
		ChannelPostDto testChannel = new ChannelPostDto(user.getId(),"TEST","TEST 채널입니다");
		Channel channel = channelService.createPublicChannel(testChannel);
		return channel;
	}

	static Message messageCreateTest(MessageService messageService, User author, Channel channel) {
		MessagePostDto cmDto = new MessagePostDto(author.getId(),channel.getId(),"테스트 메시지입니다.",null);
		Message message = messageService.createMessage(cmDto);
		System.out.println("메시지 생성: " + message.getId());
		return message;
	}

	static UserResponseDto userFindTest(UserService userService, UUID userId){
		UserResponseDto userResponseDto = userService.getUserById(userId);
		System.out.println("유저 조회 : " + userResponseDto.name());
		return userResponseDto;
	}

	static ChannelResponseDto channelFindTest(ChannelService channelService, UUID channelId) {
		ChannelResponseDto channel = channelService.find(channelId);
		System.out.println("채널 조회 : " + channel.channelName());
		return channel;
	}

	static Message messageFindTest(MessageService messageService, UUID messageId) {
		Message message = messageService.getMessagesById(messageId);
		System.out.println("메시지 조회 : " + message.getId());
		return message;
	}

	static void userUpdateTest(UserService userService, UUID userId){
		UserResponseDto user = userService.getUserById(userId);
		System.out.println("[수정 전 이름] : " + user.name());
		UserUpdateDto updateUser = new UserUpdateDto(userId,"수정된이름",null,null,null);
		userService.updateUser(updateUser);
		UserResponseDto readUser = userService.getUserById(userId);
		System.out.println("[수정 후 이름] : " + readUser.name());
	}

	static void channelUpdateTest(ChannelService channelService, UUID channelId) {
		ChannelResponseDto channel = channelService.find(channelId);
		System.out.println("[수정 전 이름] : " + channel.channelName());
		ChannelUpdateDto channelUpdateDto = new ChannelUpdateDto(channelId,"수정된 채널","내용이 수정되었습니다.");
		channelService.updateChannel(channelUpdateDto);
		ChannelResponseDto readChannel = channelService.find(channelId);
		System.out.println("[수정 후 이름] : " + readChannel.channelName());
	}

	static void messageUpdateTest(MessageService messageService, UUID messageId){
		Message message = messageService.getMessagesById(messageId);
		System.out.println("[수정 전 내용] : " + message.getContent());
		MessageUpdateDto messageDto = new MessageUpdateDto(messageId,"수정된 CONTENT");
		messageService.updateMessage(messageDto);
		System.out.println("[수정 후 내용] : " + messageService.getMessagesById(messageId).getContent());
	}

	static void userRemoveTest(UserService userService, UUID userId) {
		UserResponseDto user = userService.getUserById(userId);
		System.out.println("삭제 전 조회 : " + userService.getUserById(userId));
		userService.deleteUser(userId);
		try {
			System.out.println("삭제 후 조회 : " + userService.getUserById(userId));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void channelRemoveTest(ChannelService channelService, UUID channelId) {
		ChannelResponseDto channel = channelService.find(channelId);
		System.out.println("삭제 전 조회 : " + channelService.find(channelId));
		channelService.deleteChannel(channelId);
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
		UserResponseDto findUser = userFindTest(userService,userId);
		System.out.println("찾은 유저 : " + findUser.name());
		ChannelResponseDto findChannel = channelFindTest(channelService, channelId);
		System.out.println("찾은 채널 : " + findChannel.channelName()+", 채널 타입 : "+findChannel.channelType());
		Message findMessage = messageFindTest(messageService,messageId);
		System.out.println("찾은 메시지 : " + findMessage.getId());

		System.out.println("유저 업데이트 테스트");
		userUpdateTest(userService,userId);
		System.out.println("채널 업데이트 테스트");
		channelUpdateTest(channelService,channelId);
		System.out.println("메시지 업데이트 테스트");
		messageUpdateTest(messageService,messageId);

		System.out.println(userService.getUsers());
		System.out.println(channelService.findAllChannels());
		System.out.println(messageService.findAll());

		System.out.println("메시지 삭제 테스트");
		messageRemoveTest(messageService,messageId);

		System.out.println("채널 삭제 테스트");
		channelRemoveTest(channelService,channelId);

		System.out.println("유저 삭제 테스트");
		userRemoveTest(userService,userId);

		System.out.println(userService.getUsers());
		System.out.println(channelService.findAllChannels());
		System.out.println(messageService.findAll());
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
