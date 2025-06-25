package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FileChannelRepository implements ChannelRepository {
    @Value("${discodeit.repository.file-directory}/Channels.ser")
    private String FILE_PATH;

    @Override
    public List<Channel> findAll() {

        List<Channel> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (List<Channel>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void saveAll(List<Channel> channels) {

        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(channels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Channel findById(UUID id) {
        List<Channel> list = findAll();
        Channel channel = list.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Channel not found"));
        return channel;
    }

    @Override
    public void save(Channel channel) {
        List<Channel> list = findAll();
        if(list.stream().anyMatch(channel::equals)){
            List<Channel> updatedList = list.stream().map(c -> c.equals(channel) ? channel : c)
                                                    .collect(Collectors.toList());
            saveAll(updatedList);
        } else {
            list.add(channel);
            saveAll(list);
        }
    }

    @Override
    public void delete(Channel channel) {
        List<Channel> list = findAll();
        list.remove(channel);
        saveAll(list);
    }

}
