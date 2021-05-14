package xiaoqiangZzz.api.dealBox.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import xiaoqiangZzz.api.dealBox.entity.Attachment;

public interface AttachmentRepository extends CrudRepository<Attachment, Long>, JpaSpecificationExecutor<Attachment> {

}
