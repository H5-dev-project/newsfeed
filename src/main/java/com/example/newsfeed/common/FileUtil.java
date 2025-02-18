package com.example.newsfeed.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Component
public class FileUtil {
    @Value("${file.upload-dir}")
    private String uploadDir;
    private final String PROFILE_IMAGE_FOLDER = "profiles";
    private final String BOARDS_IMAGE_FOLDER = "boards";


    public String saveProfileImage(MultipartFile file, String usersId) throws IOException {
        String projectDir = System.getProperty("user.dir"); //프로젝트 현재 경로
        Path uploadPath = Paths.get(projectDir, uploadDir, PROFILE_IMAGE_FOLDER);

        // 업로드 디렉토리가 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        File[] existingFiles = uploadPath.toFile().listFiles((dir, name) -> name.startsWith(usersId + "_"));

        if (existingFiles != null && existingFiles.length > 0 ) {
            boolean profileIsDeleted = existingFiles[0].delete();
            if (!profileIsDeleted) {
                log.error("기존 프로필 이미지 삭제에 실패했습니다." + existingFiles[0].getName());
                throw new RuntimeException("기본 프로필 이미지 삭제에 실패했습니다.");
            }
        }

        String fileName = usersId + "_" + System.currentTimeMillis() + getFileExtension(file.getOriginalFilename());
        Path filePath = uploadPath.resolve(fileName);

        // 파일 저장
        file.transferTo(filePath.toFile());

        // 저장된 파일의 경로 반환
        return filePath.toString();
    }


    public String saveBoardImage(MultipartFile file, String usersId, String boardId) throws IOException {
        String projectDir = System.getProperty("user.dir"); //프로젝트 현재 경로
        Path uploadPath = Paths.get(projectDir, uploadDir, BOARDS_IMAGE_FOLDER);

        // 업로드 디렉토리가 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = usersId + "_" + boardId + "_"  + System.currentTimeMillis() + getFileExtension(file.getOriginalFilename());
        Path filePath = uploadPath.resolve(fileName);

        // 파일 저장
        file.transferTo(filePath.toFile());

        // 저장된 파일의 경로 반환
        return filePath.toString();
    }

    public void deleteBoardImage(String userId, String boardId) {
        String projectDir = System.getProperty("user.dir");
        Path folderPath = Paths.get(projectDir, uploadDir, BOARDS_IMAGE_FOLDER);

        Optional<File> boardImage = Optional.ofNullable(folderPath.toFile().listFiles((dir, name) -> name.startsWith(userId + "_" + boardId + "_")))
                .filter(files -> files.length > 0)
                .map(files -> files[0]);

        if (boardImage.isPresent()) {
            if (!boardImage.get().delete()) {
                log.error("게시글 이미지 삭제 실패: {}", boardImage.get().getName());
                throw new RuntimeException("게시글 이미지 삭제 실패");
            }
        } else {
            log.warn("삭제할 게시글 이미지가 없습니다: userId={}, boardId={}", userId, boardId);
        }
    }

    public void deleteAllUserImages(String usersId) {
        String projectDir = System.getProperty("user.dir");

        // ✅ 프로필 이미지 삭제
        Path profilePath = Paths.get(projectDir, uploadDir, PROFILE_IMAGE_FOLDER);
        File[] profileImage = profilePath.toFile().listFiles((dir, name) -> name.startsWith(usersId + "_"));

        if (profileImage != null && profileImage.length > 0) {
            boolean profileIsDeleted = profileImage[0].delete();
            if (!profileIsDeleted) {
                log.error(usersId+"님의 프로필 이미지 삭제에 실패했습니다." + profileImage[0].getName());
            }
        }

        // ✅ 해당 유저의 모든 게시글 이미지 삭제
        Path boardPath = Paths.get(projectDir, uploadDir, BOARDS_IMAGE_FOLDER);
        File[] boardImage = boardPath.toFile().listFiles((dir, name) -> name.startsWith(usersId + "_"));
        if (boardImage != null && boardImage.length > 0) {
            for (File file : boardImage) {
                boolean boardIsDeleted = file.delete();
                if (!boardIsDeleted) {
                    log.error(usersId + "님의 게시글 이미지 삭제에 실패했습니다.");
                }
            }
        }
    }

    /**
     * ✅ 파일 확장자 유지
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return ""; // 확장자가 없는 경우
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
