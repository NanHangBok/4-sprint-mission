//package com.sprint.mission.discodeit.mapper;
//
//import com.sprint.mission.discodeit.dto.*;
//import com.sprint.mission.discodeit.entity.Message;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BasicMessageMapper {
//    public Message toMessage(MessageCreateRequest messageCreateRequest) {
//        return new Message(messageCreateRequest.authorId(), messageCreateRequest.channelId(), messageCreateRequest.content());
//    }
//
//    public MessageDto toMessageDto(Message message) {
//        return new MessageDto(message.getId(), message.getAuthor(), message.getChannelId(), message.getContent(), message.getAttachmentIds(), message.getCreatedAt(), message.getUpdatedAt());
//    }
//}
