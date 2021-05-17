package xiaoqiangZzz.api.dealBox.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import xiaoqiangZzz.api.dealBox.entity.Goods;

import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {
  List<Goods> findAllByOrderByIdDesc();
}
