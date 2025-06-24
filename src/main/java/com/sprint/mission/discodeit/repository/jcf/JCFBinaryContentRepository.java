package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
//@Profile("jcf")
//@Primary
//@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf", matchIfMissing = true)
public class JCFBinaryContentRepository implements BinaryContentRepository {
    private final List<BinaryContent> data = new ArrayList<>();
    @Override
    public void delete(UUID id) {
        data.removeIf(bc -> bc.getId().equals(id));
    }

    @Override
    public void save(BinaryContent binaryContent) {
        if (data.contains(binaryContent)) {
            data.stream()
                    .filter(bc -> bc.equals(binaryContent))
                    .forEach(bc -> {
                        delete(bc.getId());
                    });
        }
        data.add(binaryContent);
    }

    @Override
    public BinaryContent findById(UUID id) {
        BinaryContent binaryContent = data.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst().orElseThrow(()->new IllegalArgumentException("Binary Content not found"));
        return binaryContent;
    }

    @Override
    public List<BinaryContent> findAllById(List<UUID> ids) {
        List<BinaryContent> binaryContents = new ArrayList<>();
        ids.stream()
                .forEach(id -> {
                    List<BinaryContent> binaryContentList = findAll().stream()
                            .filter(binaryContent -> binaryContent.getId().equals(id))
                            .toList();
                    binaryContents.addAll(binaryContentList);
                });
        return binaryContents;
    }

    //
    @Override
    public List<BinaryContent> findAll() {
        return data;
    }
}
