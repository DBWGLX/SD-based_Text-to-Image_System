package com.zyn.service;

import com.zyn.controller.ImageEditController;
import com.zyn.model.ImageEditRequest;
import com.zyn.model.ImageEdits;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyn.service.ImageEditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageEditController.class)
public class ImageEditServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ImageEditService imageEditService;

    private ImageEditRequest imageEditRequest;

    @BeforeEach
    public void setUp() throws Exception {
        // 读取一个测试图片并将其转换为Base64字符串
        Path path = Paths.get("src/test/resources/test-image.png");
        byte[] imageBytes = Files.readAllBytes(path);
        String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

        // 创建ImageEditRequest对象
        imageEditRequest = new ImageEditRequest();
        imageEditRequest.setImageData("data:image/png;base64," + base64Image);
        imageEditRequest.setEdits(new ImageEdits(20, 10)); // 亮度20，对比度10
    }

    @Test
    public void testEditImage() throws Exception {
        // 模拟服务层的响应
        byte[] editedImageBytes = "edited image bytes".getBytes(); // 假设这是编辑后的图片字节
        when(imageEditService.editImage(imageEditRequest)).thenReturn(ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(editedImageBytes));

        // 发送POST请求
        mockMvc.perform(MockMvcRequestBuilders.post("/api/edit/image")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imageEditRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().bytes(editedImageBytes));
    }
}