package com.root34.aurora.util;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUploadUtils.class);
    public static String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String replaceFileName = fileName + "." + FilenameUtils.getExtension(multipartFile.getResource().getFilename());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(replaceFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ex){
            throw new IOException("Could not save file: " + fileName, ex);
        }

        return replaceFileName;
    }

    public static boolean deleteFile(String uploadDir, String fileName) {

        boolean result = false;
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            result = true;
        }
        try {
            Path filePath = uploadPath.resolve(fileName);
            Files.delete(filePath);
            result = true;
        }catch (IOException ex){

           log.info("Could not delete file: " + fileName, ex);
        }

        return result;
    }
}
