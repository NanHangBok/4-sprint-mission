package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class BasedEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID id;  // 랜덤한 UUID 값으로 중복 방지
    private final Long createdAt;  // 생성 시간
    private Long updatedAt;  // 최종 업데이트 시간
    private ActiveStatus active;  // 객체가 존재하는가 ( 컨트롤 하기 적합한 가 )

    public BasedEntity() {
        Long time = System.currentTimeMillis();
        this.id = UUID.randomUUID();
        this.createdAt = time;
        this.updatedAt = time;
        this.active = ActiveStatus.INACTIVE;
    }

    /**
     * Getter
     **/
    public UUID getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public ActiveStatus isActive() {
        return active;
    }

    /**
     * Setter
     **/
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setActive(ActiveStatus active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BasedEntity that = (BasedEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
