package com.zyn.controller;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/api/image/edited")
    public ResponseEntity<Resource> getEditedImage() {
        try {
            // 指定图片的路径，使用相对路径
            Path imagePath = Paths.get("edited_image.png"); // 假设图片在当前工作目录下
            // 创建Resource对象
            Resource resource = new UrlResource(imagePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
            // 设置响应头，以便浏览器可以正确地显示图片
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("inline", "edited_image.png");
            // 返回图片文件
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
