package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.exception.BusinessLogicException;
import com.sprint.mission.discodeit.exception.ExceptionCode;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/binaryContents")
@Tag(name = "BinaryContent", description = "첨부 파일 API")
public class BinaryContentController {
    private final BinaryContentService binaryContentService;
    private final BinaryContentMapper binaryContentMapper;
    @Autowired(required = false)
    private BinaryContentStorage binaryContentStorage;

    @Operation(summary = "여러 첨부 파일 조회", operationId = "findAllByIdIn", responses = {
            @ApiResponse(responseCode = "200", description = "첨부 파일 목록 조회 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BinaryContentDto.class)))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 입력 및 검증 실패", content = @Content(examples = @ExampleObject(value = "Invalid request body | Constraint violation")))
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findBinaryContents(@Parameter(description = "조회할 첨부 파일 ID 목록") @RequestParam("binaryContentIds") List<UUID> binaryContentIds) {
        List<BinaryContent> binaryContents = binaryContentService.findAllByIdIn(binaryContentIds);
        List<BinaryContentDto> response = binaryContents.stream().map(binaryContentMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "첨부 파일 조회", operationId = "find", responses = {
            @ApiResponse(responseCode = "200", description = "첨부 파일 조회 성공", content = @Content(schema = @Schema(implementation = BinaryContentDto.class))),
            @ApiResponse(responseCode = "404", description = "첨부 파일을 찾을 수 없음", content = @Content(examples = @ExampleObject(value = "BinaryContent with id not found"))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 입력 및 검증 실패", content = @Content(examples = @ExampleObject(value = "Invalid request body | Constraint violation")))
    })
    @RequestMapping(method = RequestMethod.GET, value = "/{binary-content-id}")
    public ResponseEntity findBinaryContent(@Parameter(description = "조회할 첨부 파일 ID") @PathVariable("binary-content-id") UUID binaryContentId) {
        BinaryContent binaryContent = binaryContentService.find(binaryContentId);
        BinaryContentDto response = binaryContentMapper.toDto(binaryContent);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "파일 다운로드", operationId = "download")
    @GetMapping(value = "/{binaryContentId}/download")
    public ResponseEntity downloadBinaryContent(@Parameter(schema = @Schema(format = "uuid")) @PathVariable("binaryContentId") UUID binaryContentId) throws IOException {
        if (binaryContentStorage == null)
            throw new BusinessLogicException(ExceptionCode.BINARY_CONTENT_STORAGE_NOT_FOUND);

        BinaryContent binaryContent = binaryContentService.find(binaryContentId);
        BinaryContentDto binaryContentDto = binaryContentMapper.toDto(binaryContent);
        ResponseEntity<Resource> response = binaryContentStorage.download(binaryContentDto);
        return response;
    }
}
