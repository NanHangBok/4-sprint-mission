package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class JCFBinaryContentRepository implements BinaryContentRepository {
    private final List<BinaryContent> data = new ArrayList<>();
    @Override
    public void delete(UUID userId) {
        data.removeIf(bc -> bc.getUserId().equals(userId));
    }

    @Override
    public void save(BinaryContent binaryContent) {
        if (data.contains(binaryContent)) {
            data.stream()
                    .filter(bc -> bc.getUserId().equals(binaryContent.getUserId()))
                    .forEach(bc -> {});
        } else {
            data.add(binaryContent);
        }
    }

    @Override
    public BinaryContent findById(UUID id) {
        BinaryContent binaryContent = data.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst().orElseThrow(()->new IllegalArgumentException("Binary Content not found"));
        return binaryContent;
    }

    @Override
    public List<BinaryContent> findAll() {
        return data;
    }
}
