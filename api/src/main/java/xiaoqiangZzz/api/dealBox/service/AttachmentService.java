package xiaoqiangZzz.api.dealBox.service;

import org.springframework.web.multipart.MultipartFile;
import xiaoqiangZzz.api.dealBox.entity.Attachment;

public interface AttachmentService {
    Attachment uploadImage(MultipartFile file);
}
