package xiaoqiangZzz.api.dealBox.repository;

import xiaoqiangZzz.api.dealBox.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 用户仓库.
 */
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
  /**
   * 查询所有user.
   * @return user列表
   */
  List<User> findAll();

  User findByUsername(String name);
}
