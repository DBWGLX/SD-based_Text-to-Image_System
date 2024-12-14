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
        // 创建一个Graphics2D对象来应用编辑
        Graphics2D graphics = originalImage.createGraphics();
        graphics.setComposite(AlphaComposite.Src);

        // 创建一个WritableRaster来修改像素值
        BufferedImage bufferedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        graphics = bufferedImage.createGraphics();

        // 应用编辑指令，例如调整亮度和对比度
        int brightness = edits.getBrightness() != null ? edits.getBrightness() : 0;
        int contrast = edits.getContrast() != null ? edits.getContrast() : 0;

        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                int pixel = originalImage.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                // 应用亮度调整
                red = adjustPixelValue(red, brightness);
                green = adjustPixelValue(green, brightness);
                blue = adjustPixelValue(blue, brightness);

                // 应用对比度调整
                red = adjustContrast(red, contrast);
                green = adjustContrast(green, contrast);
                blue = adjustContrast(blue, contrast);

                // 更新像素值
                int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                bufferedImage.setRGB(x, y, newPixel);
            }
        }

        // 渲染图像
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();

        return bufferedImage;
    }

    private int adjustPixelValue(int pixelValue, int brightness) {
        return Math.min(255, Math.max(0, pixelValue + brightness));
    }

    private int adjustContrast(int pixelValue, int contrast) {
        int adjustedValue = (pixelValue * (100 + contrast)) / 100;
        return Math.min(255, Math.max(0, adjustedValue));
    }
}