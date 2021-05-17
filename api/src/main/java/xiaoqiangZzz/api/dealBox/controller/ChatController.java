package xiaoqiangZzz.api.dealBox.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xiaoqiangZzz.api.dealBox.entity.Chat;
import xiaoqiangZzz.api.dealBox.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatController {
  private final ChatService chatService;

  public ChatController(ChatService chatService) {
    this.chatService = chatService;
  }

  @GetMapping("getChatBySellerAndCurrentUser/{id}")
  @JsonView(GetBySellerIdAndCurrentUserJsonView.class)
  public Chat getBySellerIdAndCurrentUser(@PathVariable Long id) {
    return this.chatService.getBySellerIdAndCurrentUser(id);
  }

  @GetMapping("getAllByCurrentUser")
  @JsonView(GetAllByCurrentUser.class)
  public List<Chat> getAllByCurrentUser() {
    return this.chatService.getAllByCurrentUser();
  }

  private interface GetBySellerIdAndCurrentUserJsonView extends Chat.UsersJsonView {
  }

  private interface GetAllByCurrentUser extends Chat.UsersJsonView {
  }
}
