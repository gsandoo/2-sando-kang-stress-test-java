package com.ktb.stresstest.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.ktb.stresstest.exception.CommonException;
import com.ktb.stresstest.exception.ErrorCode;
import com.ktb.stresstest.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;
    private final ImageUtil imageUtil;

    @Value("${cloud.aws.s3.user-profile}")
    private String bucketUserProfile;

    //
    public String uploadUserProfile(MultipartFile multipartFile, Long userId) {
        String imageUrl = "";
        String fileName = createFileName(multipartFile.getOriginalFilename(), userId);
        ObjectMetadata objectMetadata = new ObjectMetadata();

        try (InputStream originalInputStream = multipartFile.getInputStream()) {
            // ImageUtil을 사용하여 이미지를 1:1 비율로 조정
            InputStream processedInputStream = imageUtil.cropImageToSquare(originalInputStream);

            // 조정된 이미지의 크기를 계산
            byte[] imageBytes = processedInputStream.readAllBytes();
            objectMetadata.setContentLength(imageBytes.length);
            objectMetadata.setContentType(multipartFile.getContentType());

            // 조정된 이미지를 S3에 업로드
            s3Client.putObject(new PutObjectRequest(bucketUserProfile, fileName, new ByteArrayInputStream(imageBytes),
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            String imgUrl = s3Client.getUrl(bucketUserProfile, fileName).toString();
            imageUrl = URLDecoder.decode(imgUrl, StandardCharsets.UTF_8.toString());
            log.info(URLDecoder.decode(imgUrl, StandardCharsets.UTF_8.toString()));
        } catch (IOException e) {
            log.error("Error processing image for modifyInfoId: " + userId + ", fileName: " + fileName, e);
        }

        return imageUrl;
    }


    // s3 사진 삭제 url만 주면 버킷 인식해서 알아서 지울 수 있다
    public void deleteImage(String url) {
        try {
            String bucketName = url.split("://")[1].split("\\.")[0];
            log.info(bucketName);
            String filename = url.split("://")[1].split("/", 2)[1];
            log.info(filename);
            s3Client.deleteObject(bucketName, filename);
            log.info("Deleted image {} from bucket {}", filename, bucketName);
        } catch (AmazonServiceException e) {
            log.error("S3 Error deleting : {}", e.getMessage());
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }
    }

    // (여러장)s3 사진 삭제 url만 주면 버킷 인식해서 알아서 지울 수 있다
    public void deleteMultipleImages(List<String> urls) {
        try {
            // 버킷 이름 추출 (모든 URL이 동일한 버킷에 있다고 가정)
            String bucketName = urls.get(0).split("://")[1].split("\\.")[0];

            // 삭제할 객체 목록 생성
            DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName)
                    .withKeys(urls.stream()
                            .map(url -> url.split("://")[1].split("/", 2)[1])
                            .toArray(String[]::new))
                    .withQuiet(false);

            // 객체 삭제 실행
            DeleteObjectsResult delObjRes = s3Client.deleteObjects(multiObjectDeleteRequest);
            log.info("Deleted images: {}", delObjRes.getDeletedObjects());

        } catch (AmazonServiceException e) {
            log.error("S3 Error during multi deletion: {}", e.getMessage());
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }
    }

    // 이미지 수정    (기존 사진 url, 사진, 해당객체 id)
    public String replaceImage(String url, MultipartFile multipartFile, Long id) {
        //기존 이미지 삭제
        deleteImage(url);

        String bucketName = url.split("://")[1].split("\\.")[0];
        log.info(bucketName);

        // 기존 이미지와 동일한 이름 사용
        String fileName = createFileName(multipartFile.getOriginalFilename(), id);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            // 기존 파일 덮어쓰기
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            log.info("Replaced image in bucket {}: {}", bucketName, fileName);
            String imgUrl = s3Client.getUrl(bucketName, fileName).toString();
            return (URLDecoder.decode(imgUrl, StandardCharsets.UTF_8.toString()));
        } catch (IOException e) {
            log.error("Error replacing image in bucket {}: {}", bucketName, fileName);
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }
    }

    // 이미지파일명 중복 방지
    private String createFileName(String fileName, Long popupId) {
        if (fileName.isEmpty()) {
            throw new CommonException(ErrorCode.MISSING_REQUEST_IMAGES);
        }
        // 파일 확장자 추출
        String extension = getFileExtension(fileName);
        // 파일 이름에서 확장자를 제외한 부분 추출
        String baseName = fileName.substring(0, fileName.lastIndexOf("."));
        // S3에 저장될 경로 구성: popupId 폴더 안에 원본 파일 이름으로 저장
        return popupId + "/" + baseName + extension;
    }

    // 파일 유효성 검사
    private String getFileExtension(String fileName) {
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        fileValidate.add(".tiff");
        fileValidate.add(".TIFF");
        fileValidate.add(".svg");
        fileValidate.add(".SVG");
        fileValidate.add(".webp");
        fileValidate.add(".WebP");
        fileValidate.add(".WEBP");
        fileValidate.add(".jfif");
        fileValidate.add(".JFIF");
        fileValidate.add(".HEIF");
        fileValidate.add(".heif");
        fileValidate.add(".HEIC");
        fileValidate.add(".heic");
        fileValidate.add(".heic");
        log.info("image validate : " + fileName);
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        log.info("idx file name : " + idxFileName);
        if (!fileValidate.contains(idxFileName)) {

            throw new CommonException(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

}