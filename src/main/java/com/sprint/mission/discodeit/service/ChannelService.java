package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.*;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    ChannelPublicCreateResponseDto createPublicChannel(ChannelPostDto channelPostDto);
    ChannelPrivateCreateResponseDto createPrivateChannel(ChannelPrivatePostDto channelPrivatePostDto);
    List<ChannelResponseDto> findAllByUserId(UUID userId);
    ChannelResponseDto findByChannelId(UUID channelId);
    ChannelResponseDto updateChannel(ChannelUpdateDto channelUpdateDto);
    void deleteChannel(UUID channelId);

    List<ChannelResponseDto> findAllChannels();

    void addUser(UUID channelId, UUID userId);
}
