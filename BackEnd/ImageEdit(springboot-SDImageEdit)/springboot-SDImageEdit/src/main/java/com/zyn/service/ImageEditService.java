package com.zyn.service;

import com.zyn.dao.ImageEditDao;
import com.zyn.model.ImageEditRequest;
import com.zyn.model.ImageEdits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageEditService {

    @Autowired
    private ImageEditDao imageEditDao;

    public ResponseEntity<byte[]> editImage(ImageEditRequest imageEditRequest) {
        try {
            // 从Base64编码的图片数据创建BufferedImage对象
            BufferedImage originalImage = imageEditRequest.toBufferedImage();

            // 应用编辑指令
            BufferedImage editedImage = applyEdits(originalImage, imageEditRequest.getEdits());

            // 将编辑后的图像转换为二进制数据
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(editedImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            // 返回成功响应和编辑后的图像二进制数据
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } catch (IOException e) {
            // 处理错误
            return ResponseEntity.internalServerError().body(null);
        }
    }

    private BufferedImage applyEdits(BufferedImage originalImage, ImageEdits edits) {
        int brightness = edits.getBrightness() != null ? edits.getBrightness() : 0;

        // 计算亮度比例，例如 brightness = 20 转为 scale = 1.2
        float brightnessScale = 1 + (brightness / 100.0f);

        BufferedImage editedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                int pixel = originalImage.getRGB(x, y);

                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                // 应用亮度比例调整
                red = adjustPixelValueByScale(red, brightnessScale);
                green = adjustPixelValueByScale(green, brightnessScale);
                blue = adjustPixelValueByScale(blue, brightnessScale);

                int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                editedImage.setRGB(x, y, newPixel);
            }
        }
        return editedImage;
    }

    private int adjustPixelValueByScale(int pixelValue, float scale) {
        return Math.min(255, Math.max(0, Math.round(pixelValue * scale)));
    }


    private int adjustPixelValue(int pixelValue, int brightness) {
        return Math.min(255, Math.max(0, pixelValue + brightness));
    }

    private int adjustContrast(int pixelValue, int contrast) {
        int adjustedValue = (pixelValue * (100 + contrast)) / 100;
        return Math.min(255, Math.max(0, adjustedValue));
    }
}