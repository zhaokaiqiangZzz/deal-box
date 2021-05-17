package xiaoqiangZzz.api.dealBox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import xiaoqiangZzz.api.dealBox.entity.ChatMessage;
import xiaoqiangZzz.api.dealBox.entity.Message;
import xiaoqiangZzz.api.dealBox.entity.Response;

@Controller
public class WebSocketController {
  @Autowired
  SimpMessagingTemplate simpMessagingTemplate;

  // 当客户端向服务器发送请求时，通过 `@MessageMapping` 映射 /broadcast 这个地址
  @MessageMapping("/broadcast")
  // 当服务器有消息时，会对订阅了 @SendTo 中的路径的客户端发送消息
  @SendTo("/b")
  public Response say(Message message) {
    return new Response("Welcome, " + message.getName() + "!");
  }

  @MessageMapping("/chat")
  public void chat(ChatMessage chatMessage) {
    Response response = new Response(chatMessage.getMessage());
    simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatMessage.getFromUserId()), "/msg", response);
  }
}
