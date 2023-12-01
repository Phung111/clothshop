package com.phung.clothshop;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ValidateMultipartFiles {
    public void validateMultipartFiles(MultipartFile[] multipartFiles, BindingResult bindingResult) {
        for (int i = 0; i < multipartFiles.length; i++) {
            MultipartFile multipartFile = multipartFiles[i];
            if (multipartFile != null && !multipartFile.isEmpty()) {
                // Kiểm tra kích thước file
                if (multipartFile.getSize() > 512000) {
                    String message = "File size at index " + i + " must be less than 500 KB";
                    bindingResult.rejectValue("multipartFiles[" + i + "]", "file.size", message);
                }

                // Kiểm tra loại file
                String originalFilename = multipartFile.getOriginalFilename();
                String fileExtension = originalFilename != null
                        ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1)
                        : null;
                List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
                if (fileExtension == null || !allowedExtensions.contains(fileExtension.toLowerCase())) {
                    String message = "Invalid file type at index " + i + ". Allowed types are: jpg, jpeg, png, gif";
                    bindingResult.rejectValue("multipartFiles[" + i + "]", "file.type", message);
                }
            }
        }
    }

}
