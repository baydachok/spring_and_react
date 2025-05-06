package mirea.pracs.productcrud.converter;

import java.util.List;
import java.util.stream.Stream;
import mirea.pracs.productcrud.dto.aimessage.AiMessageGetDto;
import mirea.pracs.productcrud.dto.aimessage.AiMessagePostDto;
import mirea.pracs.productcrud.dto.gpt.GptCompletionDto;
import mirea.pracs.productcrud.dto.aithread.AiThreadGetDto;
import mirea.pracs.productcrud.dto.aithread.AiThreadWrapperDto;
import mirea.pracs.productcrud.dto.gpt.GptCompletionOptionsDto;
import mirea.pracs.productcrud.dto.gpt.GptMessageDto;
import mirea.pracs.productcrud.entity.AiMessage;
import mirea.pracs.productcrud.entity.AiThread;
import mirea.pracs.productcrud.entity.User;
import mirea.pracs.productcrud.entity.enums.AiMessageRole;
import org.springframework.stereotype.Component;

@Component
public class AiEntitiesConverter {

  private static final String SYSTEM_MESSAGE = "Ты консультант в магазине портативной электроники";

  public AiThread convertToAiThread(User user) {
    return new AiThread()
        .setUser(user);
  }

  public AiThreadGetDto convertToAiThreadGetDto(AiThread aiThread) {
    var aiMessagesGetDto = aiThread.getAiMessages()
        .stream()
        .map(this::convertToAiMessageGetDto)
        .toList();
    return AiThreadGetDto.builder()
        .aiThreadId(aiThread.getAiThreadId())
        .createdTimestamp(aiThread.getCreatedTimestamp())
        .aiMessages(aiMessagesGetDto)
        .build();
  }

  public AiThreadWrapperDto convertToAiThreadWrapperDto(List<AiThread> aiThreads) {
    var aiThreadsGetDto =  aiThreads.stream()
        .map(this::convertToAiThreadGetDto)
        .toList();
    return new AiThreadWrapperDto(aiThreadsGetDto);
  }

  public AiMessage convertToAiMessage(AiMessagePostDto aiMessagePostDto, AiMessageRole role, AiThread aiThread) {
    return new AiMessage()
        .setAiThread(aiThread)
        .setRole(role)
        .setMessage(aiMessagePostDto.getMessage());
  }

  public AiMessageGetDto convertToAiMessageGetDto(AiMessage aiMessage) {
    return AiMessageGetDto.builder()
        .aiMessageId(aiMessage.getAiMessageId())
        .role(aiMessage.getRole().toString())
        .message(aiMessage.getMessage())
        .createdTimestamp(aiMessage.getCreatedTimestamp())
        .build();
  }

  public GptCompletionDto convertToGptCompletionDto(List<AiMessage> messages) {
    var gptMessagesDto = Stream.concat(
        Stream.of(getSystemMessage()),
        messages.stream().map(this::convertToGptMessageDto)
    ).toList();
    return GptCompletionDto.builder()
        .modelUri("gpt://b1gtejj5tufqde5ms9gl/yandexgpt-lite")
        .completionOptions(getCompletionOptions())
        .messages(gptMessagesDto)
        .build();
  }

  public GptMessageDto convertToGptMessageDto(AiMessage aiMessage) {
    return GptMessageDto.builder()
        .text(aiMessage.getMessage())
        .role(aiMessage.getRole().toString())
        .build();
  }

  private GptMessageDto getSystemMessage() {
    return GptMessageDto.builder()
        .role(AiMessageRole.system.toString())
        .text(SYSTEM_MESSAGE)
        .build();
  }

  private GptCompletionOptionsDto getCompletionOptions() {
    return GptCompletionOptionsDto.builder()
        .stream(false)
        .temperature(0.6)
        .maxTokens("2000")
        .build();
  }

}
