package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class JCFReadStatusRepository implements ReadStatusRepository {
    private final List<ReadStatus> data = new ArrayList<>();

    @Override
    public List<ReadStatus> findAll() {
        return data;
    }

    @Override
    public void save(ReadStatus readStatus) {
        if (data.contains(readStatus)) {
            data.stream()
                    .map(rs -> rs.equals(readStatus) ? readStatus : rs)
                    .forEach(rs -> {});
        } else {
            data.add(readStatus);
        }
    }

    @Override
    public ReadStatus findById(UUID id) {
        ReadStatus readStatus = data.stream()
                .filter(rs -> rs.getId().equals(id))
                .findFirst().orElseThrow(()->new IllegalArgumentException("Read Status not found"));
        return readStatus;
    }

    @Override
    public void delete(UUID id) {
        data.removeIf(rs -> rs.getId().equals(id));
    }
}
