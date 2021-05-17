package xiaoqiangZzz.api.dealBox.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import xiaoqiangZzz.api.dealBox.entity.Chat;
import xiaoqiangZzz.api.dealBox.entity.User;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Long>, JpaSpecificationExecutor<Chat> {
  Chat findByUsersContainingAndUsersContaining(User user, User user1);

  List<Chat> findAllByUsersContaining(User user);
}
