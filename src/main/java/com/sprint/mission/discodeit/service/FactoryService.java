package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

public interface FactoryService {
    public UserService getUserService();
    public ChannelService getChannelService();
    public MessageService getMessageService();
}
