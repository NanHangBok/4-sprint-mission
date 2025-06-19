//package com.sprint.mission.discodeit.factory;
//
//import com.sprint.mission.discodeit.repository.ChannelRepository;
//import com.sprint.mission.discodeit.repository.MessageRepository;
//import com.sprint.mission.discodeit.repository.UserRepository;
//import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
//import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
//import com.sprint.mission.discodeit.repository.file.FileUserRepository;
//import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
//import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
//import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
//import com.sprint.mission.discodeit.service.ChannelService;
//import com.sprint.mission.discodeit.service.MessageService;
//import com.sprint.mission.discodeit.service.UserService;
//import com.sprint.mission.discodeit.service.basic.BasicChannelService;
//import com.sprint.mission.discodeit.service.basic.BasicMessageService;
//import com.sprint.mission.discodeit.service.basic.BasicUserService;
////import com.sprint.mission.discodeit.service.file.FileChannelService;
////import com.sprint.mission.discodeit.service.file.FileMessageService;
//import com.sprint.mission.discodeit.service.file.FileUserService;
////import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
////import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
////import com.sprint.mission.discodeit.service.jcf.JCFUserService;
////import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
////import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
////import com.sprint.mission.discodeit.service.jcf.JCFUserService;
//
///*********************************************
// *  Service를 관리하는 Factory 클래스
// *  Service를 생성하고 주입시키는 역할
// *  2025.06.02 김민수
// *********************************************/
//public class Factory {
//    private static Factory instance;
//    private final UserService userService;
//    private final ChannelService channelService;
//    private final MessageService messageService;
//    private final UserRepository userRepository;
//    private final ChannelRepository channelRepository;
//    private final MessageRepository messageRepository;
//
//    public static Factory getJCFInstance() {
//        if (instance == null) {
//            instance = new Factory(new JCFUserRepository(), new JCFChannelRepository(), new JCFMessageRepository());
//        }
//        return instance;
//    }
//
//    public static Factory getFileInstance(){
//        if (instance == null) {
//            instance = new Factory(new FileUserRepository(), new FileChannelRepository(), new FileMessageRepository());
//        }
//        return instance;
//    }
//
//    private Factory(UserRepository userRepository, ChannelRepository channelRepository, MessageRepository messageRepository) {
//        this.userRepository = userRepository;
//        this.channelRepository = channelRepository;
//        this.messageRepository = messageRepository;
//        messageService = new BasicMessageService(getMessageRepository()
//                                ,getUserRepository(),getChannelRepository());
//        userService = new BasicUserService(getUserRepository()
//                                ,getChannelRepository()
//                                ,getMessageRepository());  // 유저서비스는 메시지서비스를 주입받는다
//        channelService = new BasicChannelService(getChannelRepository()
//                                ,getUserRepository()
//                                ,getMessageRepository());  // 채널서비스는 메시지서비스를 주입받는다
//    }
//
//    public UserService getUserService() {
//        return userService;
//    }
//
//    public ChannelService getChannelService() {
//        return channelService;
//    }
//
//    public MessageService getMessageService() {
//        return messageService;
//    }
//
//    public UserRepository getUserRepository() {
//        return userRepository;
//    }
//
//    public ChannelRepository getChannelRepository() {
//        return channelRepository;
//    }
//
//    public MessageRepository getMessageRepository() {
//        return messageRepository;
//    }
//
//}
