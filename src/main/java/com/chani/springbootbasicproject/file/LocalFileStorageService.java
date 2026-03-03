package com.chani.springbootbasicproject.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "aws.s3", name = "enabled", havingValue = "false", matchIfMissing = true)
public class LocalFileStorageService implements FileStorageService {

    @Override
    public String uploadImage(byte[] imageBytes, String fileName, String contentType) {
        try {
            Path dir = Path.of("uploads");
            Files.createDirectories(dir);
            String stored = UUID.randomUUID() + "-" + fileName;
            Path path = dir.resolve(stored);
            Files.write(path, imageBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return "/uploads/" + stored;
        } catch (IOException e) {
            throw new IllegalStateException("파일 저장에 실패했습니다.");
        }
    }
}
