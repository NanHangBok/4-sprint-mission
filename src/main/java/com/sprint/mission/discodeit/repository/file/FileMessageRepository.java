package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FileMessageRepository implements MessageRepository {
    // 대상 파일 경로와 줄바꿈 문자 설정(본인 OS 기준)
    private static final String FILE_PATH = "src/main/resources/Messages.ser";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Override
    public List<Message> findAll() {

        List<Message> list = new ArrayList<>();

        // try with resource 구문으로 작성
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (List<Message>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void saveAll(List<Message> messages) {

        // try with resource 구문으로 작성
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Message findById(UUID id) {
        List<Message> list = findAll();
        Message message = list.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
        return message;
    }

    @Override
    public void save(Message message) {
        List<Message> list = findAll();
        if(list.stream().anyMatch(message::equals)){
            List<Message> updatedList = list.stream().map(c -> c.equals(message) ? message : c)
                    .collect(Collectors.toList());
            saveAll(updatedList);
        } else {
            list.add(message);
            saveAll(list);
        }
    }

    @Override
    public void delete(Message message) {
        List<Message> list = findAll();
        list.remove(message);
        saveAll(list);
    }

    @Override
    public List<Message> findAllActive() {
        List<Message> messages = findAll();
        List<Message> activeMessages = new ArrayList<>();
        for (Message message : messages) {
            if (message.getActive().equals(ActiveStatus.ACTIVE)) {
                activeMessages.add(message);
            }
        }
        return activeMessages;
    }
}
