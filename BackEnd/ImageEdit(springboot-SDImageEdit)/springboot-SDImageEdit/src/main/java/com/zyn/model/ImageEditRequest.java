package com.zyn.model;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageEditRequest {
    private String imageData; // Base64编码的图片数据
    private ImageEdits edits;

    // Getters and Setters
    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public ImageEdits getEdits() {
        return edits;
    }

    public void setEdits(ImageEdits edits) {
        this.edits = edits;
    }

    public BufferedImage toBufferedImage() throws IOException {
        // 去除Base64字符串前缀
        String base64Image = imageData.split(",")[1];
        Decoder decoder = Base64.getDecoder();
        byte[] imageBytes = decoder.decode(base64Image);
        return ImageIO.read(new ByteArrayInputStream(imageBytes));
    }
}