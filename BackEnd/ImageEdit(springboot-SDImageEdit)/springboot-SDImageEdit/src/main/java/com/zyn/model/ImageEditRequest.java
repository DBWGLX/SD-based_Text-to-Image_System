package com.zyn.model;


public class ImageEditRequest {
    private String imagePath;
    private ImageEdits edits;

    // Getters and Setters
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ImageEdits getEdits() {
        return edits;
    }

    public void setEdits(ImageEdits edits) {
        this.edits = edits;
    }
}

