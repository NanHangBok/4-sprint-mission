package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.FactoryService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

/*********************************************
 *  Service를 관리하는 Factory 클래스
 *  Service를 생성하고 주입시키는 역할
 *  2025.06.02 김민수
 *********************************************/
public class Factory implements FactoryService {
    private static Factory instance;
    private final UserService userService;
    private final ChannelService channelService;
    private final MessageService messageService;

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }
    private Factory() {
        userService = new JCFUserService();
        channelService = new JCFChannelService();
        messageService = new JCFMessageService();
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public ChannelService getChannelService() {
        return channelService;
    }

    @Override
    public MessageService getMessageService() {
        return messageService;
    }
}
