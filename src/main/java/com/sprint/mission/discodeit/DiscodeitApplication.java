package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class DiscodeitApplication {
	static UserCreateResponseDto setupUser(UserService userService) {
		UserPostDto cuDto = new UserPostDto("woody", "woodyNickname","woody1234","woody@codeit.com",null);
		UserCreateResponseDto userResponseDto = userService.createUser(cuDto);
		return userResponseDto;
	}

	static ChannelPublicCreateResponseDto setupChannel(ChannelService channelService, UserCreateResponseDto userResponseDto) {
		ChannelPostDto ccDto = new ChannelPostDto(userResponseDto.id(),"공지","공지입니다.");
		ChannelPublicCreateResponseDto channelResponseDto = channelService.createPublicChannel(ccDto);
		return channelResponseDto;
	}

	static UserCreateResponseDto testUserCreate(UserService userService) {
		String dummyImageString = "Dummy Image";
		byte[] dummyImageBytes = dummyImageString.getBytes();
		BinaryContentPostDto bsPostDto = new BinaryContentPostDto(BinaryContentType.PROFILE, dummyImageBytes);
		UserPostDto testUser = new UserPostDto("TEST","TestNickname","PASSWORD12","TEST@EMAIL.COM",bsPostDto);
		UserCreateResponseDto userResponseDto = userService.createUser(testUser);
		return userResponseDto;
	}

	static ChannelPrivateCreateResponseDto testChannelCreate(ChannelService channelService, UUID user1Id, UUID user2Id) {
		ChannelPrivatePostDto channelPrivatePostDto = new ChannelPrivatePostDto(user1Id,user2Id);
		ChannelPrivateCreateResponseDto channelResponseDto = channelService.createPrivateChannel(channelPrivatePostDto);
		return channelResponseDto;
	}

	static MessageResponseDto messageCreateTest(MessageService messageService, UserCreateResponseDto userResponseDto, ChannelPublicCreateResponseDto channelResponseDto) {
		MessagePostDto cmDto = new MessagePostDto(userResponseDto.id(), channelResponseDto.id(),"테스트 메시지입니다.",null);
		MessageResponseDto messageResponseDto = messageService.createMessage(cmDto);
		System.out.println("메시지 생성: " + messageResponseDto.id());
		return messageResponseDto;
	}

	static UserResponseDto userFindTest(UserService userService, UUID userId){
		UserResponseDto userResponseDto = userService.findUserById(userId);
		System.out.println("유저 조회 : " + userResponseDto.nickname());
		return userResponseDto;
	}

	static ChannelResponseDto channelFindTest(ChannelService channelService, UUID channelId) {
		ChannelResponseDto channel = channelService.findByChannelId(channelId);
		System.out.println("채널 조회 : " + channel.channelName());
		return channel;
	}

	static MessageResponseDto messageFindTest(MessageService messageService, UUID messageId) {
		MessageResponseDto messageResponseDto = messageService.getMessagesById(messageId);
		System.out.println("메시지 조회 : " + messageResponseDto.id());
		return messageResponseDto;
	}

	static void userUpdateTest(UserService userService, UUID userId){
		UserResponseDto user = userService.findUserById(userId);
		System.out.println("[수정 전 이름, 프로필] : " + user.nickname() +", "+ user.profile());
		String dummyProfileString = "Dummy Profile";
		byte[] dummyProfileBytes = dummyProfileString.getBytes();
		BinaryContentPostDto dto = new BinaryContentPostDto(BinaryContentType.PROFILE,dummyProfileBytes);
		UserUpdateDto updateUser = new UserUpdateDto(userId,"수정된이름",null,null,dto);
		UserResponseDto readUser = userService.updateUser(updateUser);
		System.out.println("[수정 후 이름, 프로필] : " + readUser.nickname() +", "+ readUser.profile());
	}

	static void userProfileUpdateTest(UserService userService, UUID userId) {
		UserResponseDto user = userService.findUserById(userId);
		System.out.println("[ 수정 전 ]");
		System.out.println(user);
		String dummyProfileString = "Dummy Profile";
		byte[] dummyProfileBytes = dummyProfileString.getBytes();
		BinaryContentPostDto binaryContentPostDto = new BinaryContentPostDto(BinaryContentType.PROFILE,dummyProfileBytes);
		UserUpdateDto updateDto = new UserUpdateDto(userId,"프로필 수정",null,null,binaryContentPostDto);
		UserResponseDto readUser = userService.updateUser(updateDto);
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
		MessageResponseDto message = messageService.getMessagesById(messageId);
		System.out.println("[수정 전 내용] : " + message.content());
		MessageUpdateDto messageDto = new MessageUpdateDto(messageId,"수정된 CONTENT");
		MessageResponseDto updatedMessageResponseDto = messageService.updateMessage(messageDto);
		System.out.println("[수정 후 내용] : " + updatedMessageResponseDto.content());
	}

	static void userRemoveTest(UserService userService, UUID userId) {
		UserResponseDto user = userService.findUserById(userId);
		System.out.println("삭제 전 조회 : " + userService.findUserById(userId));
		userService.deleteUser(userId);
		try {
			System.out.println("삭제 후 조회 : " + userService.findUserById(userId));
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
		MessageResponseDto messageResponseDto = messageService.getMessagesById(messageId);
		System.out.println("삭제 전 조회 : " + messageService.getMessagesById(messageId));
		messageService.removeMessage(messageResponseDto.id());
		try {
			System.out.println("삭제 후 조회 : " + messageService.getMessagesById(messageId));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static MessageResponseDto messageFilePostTest(MessageService messageService, UUID userId, UUID channelId) {
		List<BinaryContentPostDto> binaryContentPostDtos = new ArrayList<>();
		String image1 = "IMAGE1";
		String image2 = "IMAGE2";
		String file1 = "FILE1";
		byte[] byteImage1 = image1.getBytes();
		byte[] byteImage2 = image2.getBytes();
		byte[] byteFile1 = file1.getBytes();
		binaryContentPostDtos.add(new BinaryContentPostDto(BinaryContentType.IMAGE,byteImage1));
		binaryContentPostDtos.add(new BinaryContentPostDto(BinaryContentType.IMAGE,byteImage2));
		binaryContentPostDtos.add(new BinaryContentPostDto(BinaryContentType.FILE,byteFile1));
		MessagePostDto messagePostDto = new MessagePostDto(userId,channelId,"파일테스트",binaryContentPostDtos);
		MessageResponseDto message = messageService.createMessage(messagePostDto);
		return message;
	}

	static BinaryContentResponseDto binaryContentCreateTest(BinaryContentService binaryContentService) {
		String tset = "생성 테스트";
		byte[] byteTset = tset.getBytes();
		BinaryContentPostDto binaryContentPostDto = new BinaryContentPostDto(BinaryContentType.FILE,byteTset);
		return binaryContentService.create(binaryContentPostDto);
	}

	static BinaryContentResponseDto binaryContentFindTest(BinaryContentService binaryContentService, UUID binaryContentId) {
		BinaryContentResponseDto binaryContentResponseDto = binaryContentService.find(binaryContentId);
		System.out.println("binaryContentResponseDto : " + binaryContentResponseDto);
		return binaryContentResponseDto;
	}

	static List<BinaryContentResponseDto> binaryContentFindAllByIdInTest(BinaryContentService binaryContentService, MessageResponseDto messageResponseDto) {
		List<BinaryContentResponseDto> binaryContentResponseDtos = binaryContentService.findAllByIdIn(messageResponseDto.attachmentIds());

		binaryContentResponseDtos.stream()
				.forEach(dto -> {
					System.out.println(messageResponseDto.id() + " : "+dto);
				});
		return binaryContentResponseDtos;
	}
	static void binaryContentDeleteTest(BinaryContentService binaryContentService, UUID binaryContentId) {
		System.out.println("삭제 전 조회 : " + binaryContentService.find(binaryContentId));
		binaryContentService.delete(binaryContentId);
		try {
			System.out.println("삭제 후 조회 : " + binaryContentService.find(binaryContentId));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static ReadStatusResponseDto readStatusCreateTest(ReadStatusService readStatusService, UUID userId, UUID channelId) {
		ReadStatusPostDto postDto = new ReadStatusPostDto(userId, channelId);
		return readStatusService.create(postDto);
	}

	static ReadStatusResponseDto readStatusFindTest(ReadStatusService readStatusService, UUID id) {
		ReadStatusResponseDto readStatusResponseDto = readStatusService.find(id);
		System.out.println("readStatusResponseDto : " + readStatusResponseDto);
		return  readStatusResponseDto;
	}

	static List<ReadStatusResponseDto> readStatusFindAllByUserIdTest(ReadStatusService readStatusService, UUID userId) {
		List<ReadStatusResponseDto> readStatusResponseDtos = readStatusService.findAllByUserId(userId);

		readStatusResponseDtos.stream()
				.forEach(dto -> {
					System.out.println("유저("+userId + ") : "+dto);
				});
		return readStatusResponseDtos;
	}

	static ReadStatusResponseDto readStatusUpdateTest(ReadStatusService readStatusService, UUID id) {
		ReadStatusUpdateDto readStatusUpdateDto = new ReadStatusUpdateDto(id, Instant.now());
		ReadStatusResponseDto readStatusResponseDto = readStatusService.find(id);
		System.out.println("업데이트 이전 시간 : " + readStatusResponseDto.latestTime());
		ReadStatusResponseDto updatedResponseDto = readStatusService.update(readStatusUpdateDto);
		System.out.println("업데이트 이후 시간 : " + updatedResponseDto.latestTime());
		return updatedResponseDto;
	}

	static void readStatusDeleteTest(ReadStatusService readStatusService, UUID id) {
		System.out.println("삭제 전 조회 : " + readStatusService.find(id));
		readStatusService.delete(id);
		try {
			System.out.println("삭제 후 조회 : " + readStatusService.find(id));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static UserStatusResponseDto userStatusCreateTest(UserStatusService userStatusService, UUID userId) {
		UserStatusPostDto userStatusPostDto = new UserStatusPostDto(userId, Instant.now());
		return userStatusService.create(userStatusPostDto);
	}

	static UserStatusResponseDto userStatusFindTest(UserStatusService userStatusService, UUID id) {
		UserStatusResponseDto userStatusResponseDto = userStatusService.find(id);
		System.out.println("userStatusResponseDto : " + userStatusResponseDto);
		return userStatusResponseDto;
	}

	static UserStatusResponseDto userStatusFindByUserIdTest(UserStatusService userStatusService, UUID userId) {
		UserStatusResponseDto userStatusResponseDto = userStatusService.findByUserId(userId);
		System.out.println("userStatusResponseDto : " + userStatusResponseDto);
		return userStatusResponseDto;
	}

	static List<UserStatusResponseDto> userStatusFindAllTest(UserStatusService userStatusService) {
		List<UserStatusResponseDto> userStatusResponseDtos = userStatusService.findAll();
		System.out.println("전체 순회");
		userStatusResponseDtos.stream()
				.forEach(System.out::println);
		return userStatusResponseDtos;
	}

	static UserStatusResponseDto userStatusUpdateTest(UserStatusService userStatusService, UserStatusResponseDto userStatusResponseDto) {
		UserStatusUpdateDto userStatusUpdateDto = new UserStatusUpdateDto(userStatusResponseDto.id(),userStatusResponseDto.userId(),Instant.now());
		System.out.println("업데이트 이전 : " + userStatusResponseDto.lastActiveAt());
		UserStatusResponseDto updatedUserStatus = userStatusService.update(userStatusUpdateDto);
		System.out.println("업데이트 이후 : " + updatedUserStatus.lastActiveAt());
		return updatedUserStatus;
	}

	static UserStatusResponseDto userStatusUpdateByUserIdTest(UserStatusService userStatusService, UUID userId) {
		UserStatusResponseDto findDto = userStatusFindByUserIdTest(userStatusService, userId);
		System.out.println("유저아이디로 업데이트 이전 : " + findDto.lastActiveAt());
		UserStatusResponseDto updateDto = userStatusService.updateByUserId(userId,Instant.now());
		System.out.println("유저아이디로 업데이트 이후 : " + updateDto.lastActiveAt());
		return updateDto;
	}

	static void usreStatusDeleteTest(UserStatusService userStatusService, UUID id) {
		System.out.println("삭제 전 조회 : " + userStatusService.find(id));
		userStatusService.delete(id);
		try {
			System.out.println("삭제 후 조회 : " + userStatusService.find(id));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void Test(UserService userService, ChannelService channelService, MessageService messageService, UUID userId, UUID channelId, UUID messageId
	, BinaryContentService binaryContentService, ReadStatusService readStatusService, UserStatusService userStatusService, AuthService authService) {
		UserResponseDto findUser = userFindTest(userService,userId);
		System.out.println("찾은 유저 : " + findUser.nickname());
		ChannelResponseDto findChannel = channelFindTest(channelService, channelId);
		System.out.println("찾은 채널 : " + findChannel.channelName()+", 채널 타입 : "+findChannel.channelType());
		MessageResponseDto findMessageResponseDto = messageFindTest(messageService,messageId);
		System.out.println("찾은 메시지 : " + findMessageResponseDto.id());

		System.out.println("유저 업데이트 테스트");
		userUpdateTest(userService,userId);
		System.out.println("채널 업데이트 테스트");
		channelUpdateTest(channelService,channelId);
		System.out.println("메시지 업데이트 테스트");
		messageUpdateTest(messageService,messageId);

		UserCreateResponseDto testUser = testUserCreate(userService);
		ChannelPrivateCreateResponseDto testPrivateChannel = testChannelCreate(channelService,testUser.id(),userId);

		System.out.println("프로필 변경 테스트");
		userProfileUpdateTest(userService,testUser.id());

		System.out.println();
		System.out.println("삭제 전 전체 조회");
		System.out.println(userService.findAllUsers());
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
		System.out.println(userService.findAllUsers());
		System.out.println(channelService.findAllChannels());
		System.out.println(messageService.findAll());
		System.out.println(binaryContentService.findAll());
		System.out.println(readStatusService.findAll());
		System.out.println(userStatusService.findAll());

		System.out.println();
		System.out.println("Profile 있는 User 삭제");
		userRemoveTest(userService,testUser.id());
		System.out.println(userService.findAllUsers());
		System.out.println(binaryContentService.findAll());

		System.out.println();
		System.out.println("Login 테스트");
		UserPostDto userPostDto = new UserPostDto("valid","validUser","PASSWORD12","valid@email.com",null);
		UserCreateResponseDto userCreateResponseDto = userService.createUser(userPostDto);

		ChannelPostDto channelPostDto = new ChannelPostDto(userCreateResponseDto.id(),"testChannel","테스트채널");
		ChannelPublicCreateResponseDto channelResponseDto = channelService.createPublicChannel(channelPostDto);
		channelService.addUser(channelResponseDto.id(),userCreateResponseDto.id());

		UserLoginDto validUser = new UserLoginDto("valid","PASSWORD12");
		UserLoginDto invalidUser = new UserLoginDto("fail","fail");
		UserLoginResponseDto responseValidUser = authService.login(validUser);
		System.out.println(responseValidUser);
		try {
			System.out.println(authService.login(invalidUser));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("파일 메시지 테스트");
		MessageResponseDto messageResponseDto = messageFilePostTest(messageService,responseValidUser.id(),testPrivateChannel.id());
		System.out.println(messageResponseDto);

		System.out.println();
		System.out.println("BinaryContent 테스트");
		BinaryContentResponseDto binaryContentResponseDto = binaryContentCreateTest(binaryContentService);
		binaryContentFindTest(binaryContentService,binaryContentResponseDto.id());
		binaryContentFindAllByIdInTest(binaryContentService,messageResponseDto);
		binaryContentDeleteTest(binaryContentService,binaryContentResponseDto.id());
		System.out.println("메시지 삭제 전 binaryContent : "+binaryContentService.findAll());
		System.out.println();
		messageRemoveTest(messageService,messageResponseDto.id());
		System.out.println("메시지 삭제 후 binaryContent : "+binaryContentService.findAll());

		System.out.println();
		System.out.println("ReadStatus 테스트");

		ReadStatusResponseDto readStatusResponseDto = readStatusCreateTest(readStatusService,userCreateResponseDto.id(),channelResponseDto.id());
		readStatusFindTest(readStatusService,readStatusResponseDto.id());
		System.out.println();
		readStatusFindAllByUserIdTest(readStatusService,userCreateResponseDto.id());
		System.out.println();
		readStatusUpdateTest(readStatusService,readStatusResponseDto.id());
		System.out.println();
		readStatusDeleteTest(readStatusService,readStatusResponseDto.id());

		System.out.println();
		System.out.println("UserStatus 테스트");
		UserStatusResponseDto userStatusResponseDto = userStatusCreateTest(userStatusService,userCreateResponseDto.id());
		userStatusFindTest(userStatusService,userStatusResponseDto.id());
		System.out.println();
		userStatusFindAllTest(userStatusService);
		System.out.println();
		userStatusUpdateTest(userStatusService,userStatusResponseDto);
		System.out.println();
		userStatusUpdateByUserIdTest(userStatusService,userStatusResponseDto.userId());
		System.out.println();
		usreStatusDeleteTest(userStatusService,userStatusResponseDto.id());

	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		UserService userService = context.getBean(UserService.class);
		ChannelService channelService = context.getBean(ChannelService.class);
		MessageService messageService = context.getBean(MessageService.class);

		AuthService authService = context.getBean(AuthService.class);
		BinaryContentService binaryContentService = context.getBean(BinaryContentService.class);
		ReadStatusService readStatusService = context.getBean(ReadStatusService.class);
		UserStatusService userStatusService = context.getBean(UserStatusService.class);

		System.out.println("[[[[[[[[[[[[[[[ 셋업 및 테스트 ]]]]]]]]]]]]]]]");
		// 셋업
		UserCreateResponseDto setupUser = setupUser(userService);
		ChannelPublicCreateResponseDto setupChannel = setupChannel(channelService, setupUser);

		// 테스트
		MessageResponseDto setupMessage = messageCreateTest(messageService, setupUser, setupChannel);


		Test(userService,channelService,messageService,setupUser.id(),setupChannel.id(),setupMessage.id(),binaryContentService,readStatusService,userStatusService,authService);

		System.out.println("[[[[[[[[[[[[[[[ 셋업 및 테스트 ]]]]]]]]]]]]]]]");
	}
}
