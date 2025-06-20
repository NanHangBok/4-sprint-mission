package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("file")
public class FileReadStatusRepository implements ReadStatusRepository {
    private static final String FILE_PATH = "src/main/resources/ReadUser.ser";
    @Override
    public List<ReadStatus> findAll() {

        List<ReadStatus> list = new ArrayList<>();

        // try with resource 구문으로 작성
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (List<ReadStatus>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void saveAll(List<ReadStatus> readStatus) {
        // try with resource 구문으로 작성
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(readStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(ReadStatus readStatus) {
        List<ReadStatus> list = findAll();
        if(list.stream().anyMatch(r -> r.getId().equals(readStatus.getId()))) {
            List<ReadStatus> updateList = list.stream()
                    .map(r -> r.getId().equals(readStatus.getId()) ? readStatus : r)
                    .toList();
            saveAll(updateList);
        } else {
            list.add(readStatus);
            saveAll(list);
        }
    }

    @Override
    public ReadStatus findById(UUID id) {
        List<ReadStatus> list = findAll();
        return list.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find read status with id " + id));
    }

    @Override
    public void delete(UUID id) {
        List<ReadStatus> list = findAll();
        list.removeIf(r -> r.getId().equals(id));
        saveAll(list);
    }
}
