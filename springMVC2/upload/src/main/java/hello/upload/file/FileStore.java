package hello.upload.file;

import hello.upload.domain.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * 서버에 저장할 파일 명으로 바꾼 후,
 * 바탕화면 폴더에 파일을 저장하고,
 * UploadFile 객체로 반환한다.
 */
@Slf4j
@Component
public class FileStore {

    @Value("${file.dir}")
    public String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    /**
     * 파일 1개 저장
     */
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName); // 서버에 저장할 파일명 생성

        log.info("storeFileName={}", storeFileName);

        multipartFile.transferTo(new File(getFullPath(storeFileName))); // 파일명을 가지고 만든 경로로 파일을 저장
        return new UploadFile(originalFileName, storeFileName);
    }

    /**
     * 파일 여러개 저장
     */
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {

        List<UploadFile> storeFileResult = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) { // 여러 개의 파일들 하나씩 루프를 돌려 저장
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile)); // 저장된 UploadFile 객체를 리스트에 저장
            }
        }
        return storeFileResult; // 리스트 객체 반환
    }

    /**
     * 서버에 저장할 파일명 만들기 (uuid + . + 확장자명)
     */
    private String createStoreFileName(String originalFileName) {

        int pos = originalFileName.lastIndexOf("."); // "." 의 인덱스 위치 가져오기
        String ext = originalFileName.substring(pos + 1); // 인덱스 위치를 통해 확장자 명 가져오기. png

        UUID uuid = UUID.randomUUID();
        return uuid + "." + ext; // qwer-qwer-123-qwet.png
    }




}
