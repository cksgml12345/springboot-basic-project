package com.chani.springbootbasicproject.file;

public interface FileStorageService {
    String uploadImage(byte[] imageBytes, String fileName, String contentType);
}
