//package com.sprint.mission.discodeit.mapper;
//
//import com.sprint.mission.discodeit.dto.BinaryContentPostDto;
//import com.sprint.mission.discodeit.dto.BinaryContentDto;
//import com.sprint.mission.discodeit.entity.BinaryContent;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@Component
//public class BasicBinaryContentMapper {
//    public BinaryContent toBinaryContent(BinaryContentPostDto postDto) {
//        return new BinaryContent(postDto.content(), postDto.contentType(), (long) postDto.content().length, postDto.fileName());
//    }
//
//    public BinaryContentDto toBinaryContentDto(BinaryContent binaryContent) {
//        return new BinaryContentDto(binaryContent.getId(), binaryContent.getSize(), binaryContent.getFileName(), binaryContent.getContentType());
//    }
//
//    public BinaryContentPostDto ofBinaryContentPostDto(MultipartFile file) {
//        try {
//            return new BinaryContentPostDto(file.getOriginalFilename(), file.getContentType(), file.getBytes());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
