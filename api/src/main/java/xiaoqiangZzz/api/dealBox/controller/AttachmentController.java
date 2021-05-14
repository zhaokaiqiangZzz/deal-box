package xiaoqiangZzz.api.dealBox.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xiaoqiangZzz.api.dealBox.entity.Attachment;
import xiaoqiangZzz.api.dealBox.service.AttachmentService;

@RestController
@RequestMapping("attachment")
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PutMapping("/uploadImage")
    public Attachment uploadImage(@RequestParam("file") MultipartFile file) {
        return attachmentService.uploadImage(file);
    }
}
