package com.sprint.mission.discodeit.stoarge.s3;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.storage.s3.S3BinaryContentStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class S3BinaryContentStorageTest {
    @Mock
    private S3Client s3Client;
    @Mock
    private S3Presigner presigner;
    @Mock
    private PresignedGetObjectRequest presignedGetObjectRequest;

    private S3BinaryContentStorage s3BinaryContentStorage;

    @BeforeEach
    public void setup() {
        s3BinaryContentStorage = new S3BinaryContentStorage(s3Client, presigner, "accessKey", "secretKey", "region", "bucket");
    }

    @Test
    void put() {
        // given
        UUID uuid = UUID.randomUUID();
        byte[] bytes = "test".getBytes();

        // when
        UUID binaryContentId = s3BinaryContentStorage.put(uuid, bytes);

        // then
        assertEquals(binaryContentId, uuid);
    }

    @Test
    void get() throws IOException {
        // given
        UUID uuid = UUID.randomUUID();
        URL mock = mock(URL.class);
        given(mock.openStream()).willReturn(InputStream.nullInputStream());
        given(presignedGetObjectRequest.url()).willReturn(mock);
        given(presigner.presignGetObject(any(GetObjectPresignRequest.class))).willReturn(presignedGetObjectRequest);

        // when
        InputStream is = s3BinaryContentStorage.get(uuid);

        // then
        assertNotNull(is);
    }

    @Test
    void download() throws Exception {
        // givne
        UUID uuid = UUID.randomUUID();
        BinaryContentDto binaryContentDto = new BinaryContentDto(uuid, 1L, "filename", "image/jpeg");
        given(presignedGetObjectRequest.url()).willReturn(new URL("https://amazon.com/test-url"));
        given(presigner.presignGetObject(any(GetObjectPresignRequest.class))).willReturn(presignedGetObjectRequest);
        // when
        ResponseEntity response = s3BinaryContentStorage.download(binaryContentDto);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.FOUND);
    }
}
