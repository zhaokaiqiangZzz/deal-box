package xiaoqiangZzz.api.dealBox.service;

import org.springframework.stereotype.Service;
import xiaoqiangZzz.api.dealBox.entity.Chat;
import xiaoqiangZzz.api.dealBox.entity.User;
import xiaoqiangZzz.api.dealBox.repository.ChatRepository;
import xiaoqiangZzz.api.dealBox.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
  private final UserService userService;
  private final UserRepository userRepository;
  private final ChatRepository chatRepository;

  public ChatServiceImpl(UserService userService,
                         UserRepository userRepository,
                         ChatRepository chatRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
    this.chatRepository = chatRepository;
  }

  @Override
  public Chat getBySellerIdAndCurrentUser(Long id) {
    User seller = this.userService.getById(id);
    User buyer = this.userService.getCurrentUser();
    Chat chat = this.chatRepository.findByUsersContainingAndUsersContaining(seller, buyer);
    if (chat == null) {
      Chat newChat = new Chat();
      newChat.getUsers().add(seller);
      newChat.getUsers().add(buyer);
      newChat = this.chatRepository.save(newChat);
      seller.getChats().add(newChat);
      buyer.getChats().add(newChat);
      this.userRepository.save(seller);
      this.userRepository.save(buyer);
      return newChat;

    }
    return chat;
  }

  @Override
  public List<Chat> getAllByCurrentUser() {
    return this.chatRepository.findAllByUsersContaining(this.userService.getCurrentUser());
  }
}
