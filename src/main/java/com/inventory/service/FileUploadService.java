package com.inventory.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    public String uploadImage(String url, MultipartFile file);
}
