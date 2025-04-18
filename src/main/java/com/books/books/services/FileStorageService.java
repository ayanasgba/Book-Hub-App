package com.books.books.services;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileStorageService {
    /**
     * Сохраняет файл обложки и возвращает имя сохранённого файла.
     * @param file   загруженный MultipartFile
     * @param bookId ID книги для формирования уникального имени
     * @return имя файла на диске
     */
    String storeFile(MultipartFile file, Long bookId) throws IOException;

}
