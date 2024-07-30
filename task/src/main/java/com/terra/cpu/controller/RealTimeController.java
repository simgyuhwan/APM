package com.terra.cpu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RealTimeController {

  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/sendMessage")
  public void sendMessage(String message) {
    messagingTemplate.convertAndSend("/topic/messages", message);
  }
}
