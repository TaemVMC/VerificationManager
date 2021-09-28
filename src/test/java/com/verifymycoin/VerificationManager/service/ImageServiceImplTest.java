package com.verifymycoin.VerificationManager.service;

import com.verifymycoin.VerificationManager.model.entity.Verification;
import com.verifymycoin.VerificationManager.repository.VerificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImageService 테스트")
class ImageServiceImplTest {

    private ImageService imageService;

    @Mock
    private VerificationRepository verificationRepository;

    private S3Uploader s3Uploader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(verificationRepository, s3Uploader);
    }

    Verification verification =
            Verification.builder()
                    .imageUrl("https://vmc-bucket.s3.ap-northeast-2.amazonaws.com/verification/dfffb75a-6776-4805-afd3-d0b5aed1f2f4")
                    .build();

    @Test
    @DisplayName("사진 저장")
    void saveImage() throws IOException {
//        when(verificationRepository.save(verification)).thenReturn(verification);

        assertThrows(NullPointerException.class,
                ()->{ imageService.saveImage(verification); });
        verify(verificationRepository, times(0)).save(verification);
    }

    @Test
    @DisplayName("사진 url 획득")
    void getImageUrl() throws Exception {
        String id = "615309291fc2f34655124587";
        when(verificationRepository.findById(id)).thenReturn(Optional.ofNullable(verification));

        imageService.getImageUrl(id);
        verify(verificationRepository).findById(id);
    }
}