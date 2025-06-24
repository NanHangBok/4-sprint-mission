package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class User extends BasedEntity {
    private UUID profileId;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private PresenceStatus presenceStatus;

    public User(String name, String nickname, String password, String email, UUID profileId) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileId = profileId;
        this.presenceStatus = PresenceStatus.ONLINE;
    }
    /********************
     * 유저 업데이트를 위해 status를 추가한 생성자
     * 유저는 이름, 패스워드, 이메일, 상태를 변경할 수 있다.
     * @param nickname  유저의 별명
     * @param password  유저의 패스워드
     * @param presenceStatus  유저의 상태 / enum 클래스 사용
     * @param profileId  유저 프로필 이미지
     ********************/
    public User(String nickname, String password, PresenceStatus presenceStatus, UUID profileId) {
        super();
        this.nickname = nickname;
        this.password = password;
        this.presenceStatus = presenceStatus;
        this.profileId = profileId;
    }
    @Override
    public String toString() {
        return "User{" +
                "userId=" + getId() +
                ", userCreatedAt=" + getCreatedAt() +
                ", userUpdatedAt=" + getUpdatedAt() +
                ", userName='" + name + '\'' +
                ", email= " + email +
                ", password= " + password +
                ", status=" + presenceStatus.getValue() +  // status.getValue()로 상태 값을 변수명이 아닌 변수의 값을 받는다
                '}';
    }

    /**
     * setter
     */

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPresenceStatus(PresenceStatus presenceStatus) {
        this.presenceStatus = presenceStatus;
    }

    public void setProfileId(UUID profileId) { this.profileId = profileId; }
}
