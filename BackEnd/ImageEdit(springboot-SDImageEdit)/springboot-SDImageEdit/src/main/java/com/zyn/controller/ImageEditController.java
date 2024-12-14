package com.zyn.controller;

import com.zyn.model.ImageEditRequest;
import com.zyn.service.ImageEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageEditController {

    private final ImageEditService imageEditService;

    @Autowired
    public ImageEditController(ImageEditService imageEditService) {
        this.imageEditService = imageEditService;
    }

    @PostMapping("/api/edit/image")
    public ResponseEntity<byte[]> editImage(@RequestBody ImageEditRequest imageEditRequest) {
        return imageEditService.editImage(imageEditRequest);
    }
}