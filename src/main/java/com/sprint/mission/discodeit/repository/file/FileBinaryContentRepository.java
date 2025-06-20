package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("file")
public class FileBinaryContentRepository implements BinaryContentRepository {
    private static final String FILE_PATH = "src/main/resources/BinaryContent.ser";

    @Override
    public List<BinaryContent> findAll() {

        List<BinaryContent> list = new ArrayList<>();

        // try with resource 구문으로 작성
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (List<BinaryContent>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void saveAll(List<BinaryContent> binaryContents) {
        // try with resource 구문으로 작성
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(binaryContents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UUID id) {
        List<BinaryContent> binaryContents = findAll();
        BinaryContent binaryContent = binaryContents.stream()
                .filter(biContent -> biContent.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find binary content with id " + id));
        binaryContents.remove(binaryContent);
        saveAll(binaryContents);
    }

    @Override
    public void save(BinaryContent binaryContent) {
        List<BinaryContent> binaryContents = findAll();
        if(binaryContents.stream()
                        .anyMatch(biContent -> biContent.equals(binaryContent.getId())))
            throw new RuntimeException("Binary content with id " + binaryContent.getId() + " already exists");
        binaryContents.add(binaryContent);
        saveAll(binaryContents);
    }

    @Override
    public BinaryContent findById(UUID id) {
        List<BinaryContent> binaryContents = findAll();
        return binaryContents.stream().filter(binaryContent -> binaryContent.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find binary content with id " + id));
    }

}