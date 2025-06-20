package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("file")
public class FileUserStatusRepository implements UserStatusRepository {
    private static final String FILE_PATH = "src/main/resources/UserStatus.ser";

    @Override
    public List<UserStatus> findAll() {

        List<UserStatus> list = new ArrayList<>();

        // try with resource 구문으로 작성
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (List<UserStatus>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void saveAll(List<UserStatus> userStatus) {
        // try with resource 구문으로 작성
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(userStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UUID id) {
        List<UserStatus> list = findAll();
        list.removeIf(r -> r.getId().equals(id));
        saveAll(list);
    }

    @Override
    public UserStatus findById(UUID userId) {
        List<UserStatus> list = findAll();
        return list.stream()
                .filter(userStatus -> userStatus.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("UserStatus not found"));
    }

    @Override
    public void save(UserStatus userStatus) {
        List<UserStatus> list = findAll();
        if (list.stream()
                .anyMatch(us -> us.getId().equals(userStatus.getId()))) {
            List<UserStatus> updateList = list.stream()
                    .map(us -> us.equals(userStatus) ? userStatus : us)
                    .toList();
            saveAll(updateList);
        } else {
            list.add(userStatus);
            saveAll(list);
        }
    }
}