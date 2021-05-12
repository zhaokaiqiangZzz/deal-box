package xiaoqiangZzz.api.dealBox.repository;

import xiaoqiangZzz.api.dealBox.entity.Attachment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends CrudRepository<Attachment, Long>, JpaSpecificationExecutor<Attachment> {
}
