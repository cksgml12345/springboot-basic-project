package com.chani.springbootbasicproject.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageProcessor {

    public ProcessedImage process(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }

        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
            }

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Thumbnails.of(new ByteArrayInputStream(file.getBytes()))
                    .size(1200, 1200)
                    .outputFormat("jpg")
                    .outputQuality(0.85)
                    .toOutputStream(output);

            String baseName = file.getOriginalFilename() == null ? "image" : file.getOriginalFilename();
            String safeName = baseName.replaceAll("[^a-zA-Z0-9._-]", "_");
            if (!safeName.toLowerCase().endsWith(".jpg") && !safeName.toLowerCase().endsWith(".jpeg")) {
                safeName = safeName + ".jpg";
            }

            return new ProcessedImage(output.toByteArray(), safeName, "image/jpeg");
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 처리에 실패했습니다.");
        }
    }

    public record ProcessedImage(byte[] bytes, String fileName, String contentType) {}
}
