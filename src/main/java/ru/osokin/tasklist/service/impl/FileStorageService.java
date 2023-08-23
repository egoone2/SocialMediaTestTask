package ru.osokin.tasklist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.osokin.tasklist.domain.exception.FileStorageException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * Сейчас файлы-картинки сохраняются на этой же машине (сервере) без сжатия.
 * В перспективе возможна отправка на сторонние ресурсы или сжатие, дабы не захламлять
 * сервер огромным количеством тяжелых файлов.
 */
@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${upload.path}")
    private String uploadPath;


    public String storeFile(MultipartFile file) {
        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();


            try {
                file.transferTo(new File(uploadPath + "/" + resultFileName));
            } catch (IOException e) {
                throw new FileStorageException("Cannot store file!");
            }

            return resultFileName;
        }
        return "";
    }


}


