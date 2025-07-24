//package com.sprint.mission.discodeit.mapper;
//
//import com.sprint.mission.discodeit.dto.ChannelDto;
//import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
//import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
//import com.sprint.mission.discodeit.dto.UserDto;
//import com.sprint.mission.discodeit.entity.Channel;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//import java.time.Instant;
//import java.util.List;
//
//@Mapper(componentModel = "spring", uses = {UserMapper.class})
//public interface TmpChannelMapper {
//    @Mapping(target = "type", constant = "PUBLIC")
//    Channel toPublicChannel(PublicChannelCreateRequest publicChannelCreateRequest);
//
//    @Mapping(target = "type", constant = "PRIVATE")
//    Channel toPrivateChannel(PrivateChannelCreateRequest privateChannelCreateRequest);
//
//    ChannelDto toChannelDto(Channel channel, Instant lastMessageAt);
//
//    ChannelDto ofChannelDto(Channel channel, List<UserDto> participants, Instant lastMessageAt);
//
//}
