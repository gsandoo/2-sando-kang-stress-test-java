package com.ktb.stresstest.usecase;

import com.ktb.stresstest.annotation.UseCase;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@UseCase
public interface S3UseCase {

    String uploadImage(MultipartFile multipartFile, Long userId);

    void deleteImage(String url);

    void deleteMultipleImages(List<String> urls);

    String replaceImage(String url, MultipartFile multipartFile, Long id);

    String createFileName(String fileName, Long popupId);

    String getFileExtension(String fileName);
}
