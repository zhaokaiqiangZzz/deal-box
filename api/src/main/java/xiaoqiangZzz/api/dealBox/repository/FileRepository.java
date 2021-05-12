package xiaoqiangZzz.api.dealBox.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import xiaoqiangZzz.api.dealBox.entity.File;

public interface FileRepository extends CrudRepository<File, Long>, JpaSpecificationExecutor<File> {

    File findTopBySha1AndMd5(String sh1, String md5);
}
