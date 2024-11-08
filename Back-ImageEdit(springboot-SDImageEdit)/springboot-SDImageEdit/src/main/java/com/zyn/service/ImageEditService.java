package com.zyn.service;

import com.zyn.dao.ImageEditDao;
import com.zyn.model.ImageEditRequest;
import com.zyn.model.ImageEdits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;


@Service
public class ImageEditService {

//    private final ImageEditDao imageEditDao;
//
//    @Autowired
//    public ImageEditService(ImageEditDao imageEditDao) {
//        this.imageEditDao = imageEditDao;
//    }
    @Autowired
    private ImageEditDao imageEditDao;

    public ResponseEntity<?> editImage(ImageEditRequest imageEditRequest) {
        try {
            // 读取原始图像
            BufferedImage originalImage = imageEditDao.readImage(imageEditRequest.getImagePath());

            // 应用编辑指令
            BufferedImage editedImage = applyEdits(originalImage, imageEditRequest.getEdits());

            // 保存编辑后的图像
            imageEditDao.saveImage(editedImage, "edited_image.png");

            // 返回成功响应和编辑后的图像URL
            return ResponseEntity.ok().body(Map.of("success", true, "message", "图像编辑成功", "image_url", "edited_image.png"));
        } catch (IOException e) {
            // 处理错误
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", "图像编辑失败"));
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

    public String getEditedImagePath() {
        return "edited_image.png";
    }
}