package com.verbovskiy.server.util.file_image;

import com.verbovskiy.server.controller.Constant;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FileImage {
    private static final Logger logger = LogManager.getLogger(FileImage.class);

    public static String saveImage(byte[] bytes) {
        String randFilename = "";
        try {
            randFilename = Constant.IMAGE_PATH_NAME + UUID.randomUUID() + ".jpg";
            InputStream imageIN = new ByteArrayInputStream(bytes);
            BufferedImage bufferedImage = ImageIO.read(imageIN);
            ImageIO.write(bufferedImage, "jpg", new File(randFilename));
        } catch (IOException e) {
            logger.log(Level.ERROR, "error while download image" + e);
        }
        return randFilename;
    }
}
