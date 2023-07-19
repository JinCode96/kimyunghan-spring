package hello.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {
    private String uploadFileName; // 사용자가 업로드한 파일명
    private String storeFileName; // 서버에 저장할 파일명 (겹치지 않게 해야한다)
}
