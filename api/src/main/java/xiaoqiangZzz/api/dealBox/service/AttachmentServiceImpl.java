package xiaoqiangZzz.api.dealBox.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xiaoqiangZzz.api.dealBox.entity.Attachment;
import xiaoqiangZzz.api.dealBox.repository.AttachmentRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final CommonService commonService;
    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(CommonService commonService,
                                 AttachmentRepository attachmentRepository) {
        this.commonService = commonService;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Attachment uploadImage(MultipartFile file) {
        Attachment attachment = new Attachment();
        String imageUrl = this.commonService.uploadImage(file);
        attachment.setUrl(imageUrl);
        return this.attachmentRepository.save(attachment);
    }
}
