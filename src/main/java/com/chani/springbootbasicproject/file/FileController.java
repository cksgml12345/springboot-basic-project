package com.chani.springbootbasicproject.file;

import com.chani.springbootbasicproject.file.dto.FileUploadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final ImageProcessor imageProcessor;
    private final FileStorageService fileStorageService;

    public FileController(ImageProcessor imageProcessor, FileStorageService fileStorageService) {
        this.imageProcessor = imageProcessor;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(value = "/images", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public FileUploadResponse uploadImage(@RequestParam("file") MultipartFile file) {
        ImageProcessor.ProcessedImage processed = imageProcessor.process(file);
        String fileUrl = fileStorageService.uploadImage(
                processed.bytes(),
                processed.fileName(),
                processed.contentType()
        );
        return new FileUploadResponse(fileUrl);
    }
}
