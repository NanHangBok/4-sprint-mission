package com.sprint.mission.discodeit.dto;

public record UserPostDto(
        String name,
        String password,
        String email,
        BinaryContentPostDto binaryContentPostDto
) { }
