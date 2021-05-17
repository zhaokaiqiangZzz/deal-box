package xiaoqiangZzz.api.dealBox.service;

import xiaoqiangZzz.api.dealBox.entity.Chat;

import java.util.List;

public interface ChatService {

  Chat getBySellerIdAndCurrentUser(Long id);

  List<Chat> getAllByCurrentUser();
}
