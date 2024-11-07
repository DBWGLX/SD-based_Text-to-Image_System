package com.zyn.controller;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zyn.model.ImageEditRequest;
import com.zyn.service.ImageEditService;

@RestController
public class ImageEditController {

    private final ImageEditService imageEditService;

    @Autowired
    public ImageEditController(ImageEditService imageEditService) {
        this.imageEditService = imageEditService;
    }

    @PostMapping("/api/edit/image")
    public ResponseEntity<?> editImage(@RequestBody ImageEditRequest imageEditRequest) {
        try {
            // 调用服务层方法处理图像编辑
            return imageEditService.editImage(imageEditRequest);
        } catch (Exception e) {
            // 处理错误
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", "图像编辑失败"));
        }
    }
}