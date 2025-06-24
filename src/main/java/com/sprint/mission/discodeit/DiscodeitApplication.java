package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;

@SpringBootApplication
public class DiscodeitApplication {
	static User setupUser(UserService userService) {
		UserPostDto cuDto = new UserPostDto("woody", "woodyNickname","woody1234","woody@codeit.com",null);
		User user = userService.createUser(cuDto);
		return user;
	}

	static Channel setupChannel(ChannelService channelService, User user) {
		ChannelPostDto ccDto = new ChannelPostDto(user.getId(),"공지","공징입니다.");
		Channel channel = channelService.createPublicChannel(ccDto);
		return channel;
	}

	static User testUserCreate(UserService userService) {
		String dummyImageString = "Dummy Image";
		byte[] dummyImageBytes = dummyImageString.getBytes();

		BinaryContentPostDto bsPostDto = new BinaryContentPostDto(BinaryContentType.PROFILE, dummyImageBytes);
		UserPostDto testUser = new UserPostDto("TEST","TestNickname","PASSWORD12","TEST@EMAIL.COM",bsPostDto);
		User user = userService.createUser(testUser);
		return user;
	}

	static Channel testChannelCreate(ChannelService channelService, UUID user1Id, UUID user2Id) {
		ChannelPrivatePostDto channelPrivatePostDto = new ChannelPrivatePostDto(user1Id,user2Id);
		Channel channel = channelService.createPrivateChannel(channelPrivatePostDto);
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
		System.out.println("유저 조회 : " + userResponseDto.nickname());
		return userResponseDto;
	}

	static ChannelResponseDto channelFindTest(ChannelService channelService, UUID channelId) {
		ChannelResponseDto channel = channelService.findByChannelId(channelId);
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
		System.out.println("[수정 전 이름, 프로필] : " + user.nickname() +", "+ user.profile());
		String dummyProfileString = "Dummy Profile";
		byte[] dummyProfileBytes = dummyProfileString.getBytes();
		BinaryContentPostDto dto = new BinaryContentPostDto(BinaryContentType.PROFILE,dummyProfileBytes);
		UserUpdateDto updateUser = new UserUpdateDto(userId,"수정된이름",null,null,dto);
		userService.updateUser(updateUser);
		UserResponseDto readUser = userService.getUserById(userId);
		System.out.println("[수정 후 이름, 프로필] : " + readUser.nickname() +", "+ readUser.profile());
	}

	static void userProfileUpdateTest(UserService userService, UUID userId) {
		UserResponseDto user = userService.getUserById(userId);
		System.out.println("[ 수정 전 ]");
		System.out.println(user);
		String dummyProfileString = "Dummy Profile";
		byte[] dummyProfileBytes = dummyProfileString.getBytes();
		BinaryContentPostDto binaryContentPostDto = new BinaryContentPostDto(BinaryContentType.PROFILE,dummyProfileBytes);
		UserUpdateDto updateDto = new UserUpdateDto(userId,"프로필 수정",null,null,binaryContentPostDto);
		userService.updateUser(updateDto);
		UserResponseDto readUser = userService.getUserById(userId);
		System.out.println("[수정 후]");
		System.out.println(readUser);
	}

	static void channelUpdateTest(ChannelService channelService, UUID channelId) {
		ChannelResponseDto channel = channelService.findByChannelId(channelId);
		System.out.println("[수정 전 이름] : " + channel.channelName());
		ChannelUpdateDto channelUpdateDto = new ChannelUpdateDto(channelId,"수정된 채널","내용이 수정되었습니다.");
		channelService.updateChannel(channelUpdateDto);
		ChannelResponseDto readChannel = channelService.findByChannelId(channelId);
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
		ChannelResponseDto channel = channelService.findByChannelId(channelId);
		System.out.println("삭제 전 조회 : " + channelService.findByChannelId(channelId));
		channelService.deleteChannel(channelId);
		try {
			System.out.println("삭제 후 조회 : " + channelService.findByChannelId(channelId));
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

	static void Test(UserService userService, ChannelService channelService, MessageService messageService, UUID userId, UUID channelId, UUID messageId
	, BinaryContentService binaryContentService, ReadStatusService readStatusService, UserStatusService userStatusService) {
		UserResponseDto findUser = userFindTest(userService,userId);
		System.out.println("찾은 유저 : " + findUser.nickname());
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

		User testUser = testUserCreate(userService);
		Channel testPrivateChannel = testChannelCreate(channelService,testUser.getId(),userId);

		System.out.println("프로필 변경 테스트");
		userProfileUpdateTest(userService,testUser.getId());

		System.out.println();
		System.out.println("삭제 전 전체 조회");
		System.out.println(userService.getUsers());
		System.out.println(channelService.findAllChannels());
		System.out.println(messageService.findAll());
		System.out.println(binaryContentService.findAll());
		System.out.println(readStatusService.findAll());
		System.out.println(userStatusService.findAll());
		System.out.println();

		System.out.println("메시지 삭제 테스트");
		messageRemoveTest(messageService,messageId);

		System.out.println("채널 삭제 테스트");
		channelRemoveTest(channelService,channelId);

		System.out.println("유저 삭제 테스트");
		userRemoveTest(userService,userId);

		System.out.println();
		System.out.println(userService.getUsers());
		System.out.println(channelService.findAllChannels());
		System.out.println(messageService.findAll());
		System.out.println(binaryContentService.findAll());
		System.out.println(readStatusService.findAll());
		System.out.println(userStatusService.findAll());

		System.out.println();
		System.out.println("Profile 있는 User 삭제");
		userRemoveTest(userService,testUser.getId());
		System.out.println(userService.getUsers());
		System.out.println(binaryContentService.findAll());
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		UserService userService = context.getBean(UserService.class);
		ChannelService channelService = context.getBean(ChannelService.class);
		MessageService messageService = context.getBean(MessageService.class);

		BinaryContentService binaryContentService = context.getBean(BinaryContentService.class);
		ReadStatusService readStatusService = context.getBean(ReadStatusService.class);
		UserStatusService userStatusService = context.getBean(UserStatusService.class);

		System.out.println("[[[[[[[[[[[[[[[ 셋업 및 테스트 ]]]]]]]]]]]]]]]");
		// 셋업
		User setupUser = setupUser(userService);
		Channel setupChannel = setupChannel(channelService, setupUser);

		// 테스트
		Message setupMessage = messageCreateTest(messageService, setupUser, setupChannel);

		Test(userService,channelService,messageService,setupUser.getId(),setupChannel.getId(),setupMessage.getId(),binaryContentService,readStatusService,userStatusService);
		System.out.println("[[[[[[[[[[[[[[[ 셋업 및 테스트 ]]]]]]]]]]]]]]]");
	}
}
