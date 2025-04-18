package com.books.books.services.Impl;

import com.books.books.services.FileStorageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path uploadDir;

    public FileStorageServiceImpl(@Value("${file.upload-dir}") String uploadDirStr) throws IOException {
        this.uploadDir = Paths.get(uploadDirStr)
                .toAbsolutePath()
                .normalize();
        Files.createDirectories(this.uploadDir);
    }

    @Override
    public String storeFile(MultipartFile file, Long bookId) throws IOException {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String filename = "book-" + bookId + "-" + originalFilename;
        Path target = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }
}
