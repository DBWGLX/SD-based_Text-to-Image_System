package com.zyn.dao;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class ImageEditDao {

    public BufferedImage readImage(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        return ImageIO.read(imageFile);
    }

    public void saveImage(BufferedImage image, String path) throws IOException {
        File outputFile = new File(path);
        ImageIO.write(image, "png", outputFile);
    }
}