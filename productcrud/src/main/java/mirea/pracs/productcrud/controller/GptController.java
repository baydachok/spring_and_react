package mirea.pracs.productcrud.controller;

import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.aimessage.AiMessageGetDto;
import mirea.pracs.productcrud.dto.aimessage.AiMessagePostDto;
import mirea.pracs.productcrud.dto.aithread.AiThreadGetDto;
import mirea.pracs.productcrud.dto.aithread.AiThreadWrapperDto;
import mirea.pracs.productcrud.service.GptService;
import mirea.pracs.productcrud.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/ai/threads")
public class GptController {

  private final UserService userService;
  private final GptService gptService;

  public GptController(UserService userService, GptService gptService) {
    this.userService = userService;
    this.gptService = gptService;
  }

  @GetMapping
  public ResponseEntity<AiThreadWrapperDto> getThreads(
      Authentication authentication
  ) {
    var user = userService.findOneByAuthentication(authentication);
    var aiThreadWrapperDto = gptService.getThreads(user);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(aiThreadWrapperDto);
  }

  @GetMapping("/{threadId}")
  public ResponseEntity<AiThreadGetDto> getThread(
      @PathVariable("threadId") Long threadId,
      Authentication authentication
  ) {
    var user = userService.findOneByAuthentication(authentication);
    var aiThreadGetDto = gptService.getThread(threadId, user);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(aiThreadGetDto);
  }

  @PostMapping
  public ResponseEntity<PostResponse> startThread(
      Authentication authentication
  ) {
    var user = userService.findOneByAuthentication(authentication);
    var postResponse = gptService.createThread(user);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(postResponse);
  }

  @PostMapping("/{threadId}/messages")
  public ResponseEntity<AiMessageGetDto> sendMessage(
      @RequestBody AiMessagePostDto aiMessagePostDto,
      @PathVariable("threadId") Long threadId,
      Authentication authentication
  ) {
    var user = userService.findOneByAuthentication(authentication);
    var aiThread = gptService.getAiThreadEntity(threadId, user);
    var aiMessageGetDto = gptService.sendMessage(aiMessagePostDto, aiThread);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(aiMessageGetDto);
  }

}
