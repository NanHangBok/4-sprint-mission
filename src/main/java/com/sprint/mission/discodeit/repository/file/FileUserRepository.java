package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FileUserRepository implements UserRepository {

    // 대상 파일 경로와 줄바꿈 문자 설정(본인 OS 기준)
    private static final String FILE_PATH = "src/main/resources/Users.ser";

    @Override
    public List<User> findAll() {

        List<User> list = new ArrayList<>();

        // try with resource 구문으로 작성
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void saveAll(List<User> users) {

        // try with resource 구문으로 작성
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(UUID id) {
        List<User> list = findAll();
        User user = list.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user;
    }

    @Override
    public User findByName(String name) {
        List<User> list = findAll();
        User user = list.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return user;
    }

    @Override
    public void save(User user) {
        List<User> list = findAll();
        if(list.stream().anyMatch(user::equals)){
            List<User> updatedList = list.stream().map(c -> c.equals(user) ? user : c)
                    .collect(Collectors.toList());
            saveAll(updatedList);
        } else {
            list.add(user);
            saveAll(list);
        }
    }

    @Override
    public void delete(User user) {
        List<User> list = findAll();
        list.remove(user);
        saveAll(list);
    }
}
