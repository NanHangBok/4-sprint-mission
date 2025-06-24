package com.sprint.mission.discodeit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ReadStatus extends BasedEntity{
    private UUID userId;
    private UUID channelId;
    private Instant latestTime;

    @Override
    public String toString() {
        return "ReadStatus{" +
                "userId=" + userId +
                ", channelId=" + channelId +
                ", latestTime=" + latestTime +
                '}';
    }
}
