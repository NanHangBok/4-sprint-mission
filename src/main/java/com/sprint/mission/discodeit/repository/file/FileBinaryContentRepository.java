package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
//@Profile("file")
//@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
public class FileBinaryContentRepository implements BinaryContentRepository {
    //    private String FILE_PATH = "src/main/resources/BinaryContent.ser";
    @Value("${discodeit.repository.file-directory}/BinaryContents.ser")
    private String FILE_PATH;

    @Override
    public List<BinaryContent> findAll() {
        List<BinaryContent> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (List<BinaryContent>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<BinaryContent> findAllById(List<UUID> ids) {
        List<BinaryContent> list = new ArrayList<>();
        ids.stream()
                .forEach(id -> {
                    List<BinaryContent> binaryContentList = findAll().stream()
                            .filter(content -> content.getId().equals(id))
                            .toList();
                    list.addAll(binaryContentList);
                });
        return list;
    }

    public void saveAll(List<BinaryContent> binaryContents) {
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
                        .anyMatch(biContent -> biContent.equals(binaryContent)))
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