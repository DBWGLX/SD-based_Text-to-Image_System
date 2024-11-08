package com.zyn.service;

import com.zyn.dao.ImageEditDao;
import com.zyn.model.ImageEditRequest;
import com.zyn.model.ImageEdits;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ImageEditServiceTest {

    @MockBean
    private ImageEditDao imageEditDao;

    @Autowired
    private ImageEditService imageEditService;

    @Test
    public void testEditImage() throws IOException {
        // Arrange
        ImageEditRequest imageEditRequest = new ImageEditRequest();
        imageEditRequest.setImagePath("path/to/image.png");
        ImageEdits edits = new ImageEdits();
        edits.setBrightness(10);
        edits.setContrast(20);
        imageEditRequest.setEdits(edits);

        BufferedImage mockImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        when(imageEditDao.readImage(anyString())).thenReturn(mockImage);

        // Act
        ResponseEntity<?> response = imageEditService.editImage(imageEditRequest);

        // Assert
        verify(imageEditDao, times(1)).readImage("path/to/image.png");
        verify(imageEditDao, times(1)).saveImage(mockImage, "edited_image.png");
        assert response.getBody().equals(Map.of("success", true, "message", "图像编辑成功", "image_url", "edited_image.png"));
    }
}