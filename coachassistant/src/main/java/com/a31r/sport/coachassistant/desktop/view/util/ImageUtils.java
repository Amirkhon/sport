package com.a31r.sport.coachassistant.desktop.view.util;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.ByteArrayInputStream;

/**
 * Created by bahodurova on 1/11/2018.
 */
public class ImageUtils {

    private ImageUtils() {}

    public static Image cropAndFit(byte[] photo, int fitWidth, int fitHeight) {
        Image image = new Image(new ByteArrayInputStream(photo));
        int scale = image.getWidth() > image.getHeight() ? (int) image.getHeight() / fitHeight : (int) image.getWidth() / fitWidth;
        int x = (int) (image.getWidth() - (scale * fitWidth)) / 2;
        int y = (int) (image.getHeight() - (scale * fitHeight)) / 2;
        return new WritableImage(image.getPixelReader(), x, y,scale * fitWidth, scale * fitHeight);
    }

}
