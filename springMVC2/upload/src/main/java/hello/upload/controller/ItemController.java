package hello.upload.controller;

import hello.upload.domain.Item;
import hello.upload.domain.ItemRepository;
import hello.upload.domain.UploadFile;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form) {
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {

        // 폴더에 파일 저장 후, 서버에 저장할 파일명을 반환
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        // 데이터베이스에 저장
        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setAttachFile(attachFile);
        item.setImageFiles(storeImageFiles);
        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());
        return "redirect:/items/{itemId}";
    }

    @GetMapping("/items/{itemId}")
    public String items(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "item-view";
    }

    /**
     * 이미지 보여주기
     * 파일명이 왔을 때, UrlResource 로 실제 파일에 접근, 리소스를 가지고 브라우저에 전송하여 이미지를 보여준다.
     */
    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
        // 보안을 위해 체크로직 넣기
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }

    /**
     * 파일 다운로드
     */
    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {

        Item item = itemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8); // 한글이나 특수문자가 깨질 수 있어서 인코딩 해줘야함.
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\""; // 브라우저가 이것을 보고 다운로드를 진행한다. 첨부파일이라는 것을 인식

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
