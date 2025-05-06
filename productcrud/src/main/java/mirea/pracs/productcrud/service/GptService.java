package mirea.pracs.productcrud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import mirea.pracs.productcrud.converter.AiEntitiesConverter;
import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.aimessage.AiMessageGetDto;
import mirea.pracs.productcrud.dto.aimessage.AiMessagePostDto;
import mirea.pracs.productcrud.dto.aithread.AiThreadGetDto;
import mirea.pracs.productcrud.dto.aithread.AiThreadWrapperDto;
import mirea.pracs.productcrud.entity.AiMessage;
import mirea.pracs.productcrud.entity.AiThread;
import mirea.pracs.productcrud.entity.User;
import mirea.pracs.productcrud.entity.enums.AiMessageRole;
import mirea.pracs.productcrud.entity.enums.UserRole;
import mirea.pracs.productcrud.exceptions.ForbiddenException;
import mirea.pracs.productcrud.exceptions.InternalServerErrorException;
import mirea.pracs.productcrud.exceptions.NotFoundException;
import mirea.pracs.productcrud.repository.AiMessageRepository;
import mirea.pracs.productcrud.repository.AiThreadRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class GptService {

  private static final String YANDEX_GPT_POST_COMPLETION_URL = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion";

  private final AiThreadRepository aiThreadRepository;
  private final AiMessageRepository aiMessageRepository;
  private final AiEntitiesConverter aiEntitiesConverter;
  private final RestClient gptRestClient;
  private final ObjectMapper objectMapper;

  public GptService(
      AiThreadRepository aiThreadRepository,
      AiMessageRepository aiMessageRepository,
      AiEntitiesConverter aiEntitiesConverter,
      RestClient gptRestClient,
      ObjectMapper objectMapper
  ) {
    this.aiThreadRepository = aiThreadRepository;
    this.aiMessageRepository = aiMessageRepository;
    this.aiEntitiesConverter = aiEntitiesConverter;
    this.gptRestClient = gptRestClient;
    this.objectMapper = objectMapper;
  }

  public AiThread getAiThreadEntity(Long threadId, User user) {
    var aiThread = aiThreadRepository.findById(threadId)
        .orElseThrow(() -> new NotFoundException(
            String.format("AiThread with id %d not found", threadId)
        ));
    requireAccessThreadPermission(user, aiThread);
    return aiThread;
  }

  public AiThreadWrapperDto getThreads(User user) {
    var aiThreads = aiThreadRepository.findAllByUser(user);
    return aiEntitiesConverter.convertToAiThreadWrapperDto(aiThreads);
  }

  public AiThreadGetDto getThread(Long threadId, User user) {
    var aiThread = aiThreadRepository.findById(threadId)
        .orElseThrow(() -> new NotFoundException(
            String.format("AiThread with id %d not found", threadId)
        ));
    requireAccessThreadPermission(user, aiThread);
    return aiEntitiesConverter.convertToAiThreadGetDto(aiThread);
  }

  @Transactional
  public PostResponse createThread(User user) {
    var aiThread = aiEntitiesConverter.convertToAiThread(user);
    aiThreadRepository.save(aiThread);
    return new PostResponse(aiThread.getAiThreadId());
  }

  @Transactional
  public AiMessageGetDto sendMessage(AiMessagePostDto aiMessagePostDto, AiThread aiThread) {
    var userMessage = aiEntitiesConverter.convertToAiMessage(
        aiMessagePostDto,
        AiMessageRole.user,
        aiThread
    );
    aiMessageRepository.save(userMessage);
    aiThread.getAiMessages().add(userMessage);

    var responseString = sendChatCompletion(aiThread.getAiMessages());
    var assistantMessage = aiEntitiesConverter.convertToAiMessage(
        new AiMessagePostDto().setMessage(responseString),
        AiMessageRole.assistant,
        aiThread
    );
    aiMessageRepository.save(assistantMessage);
    return aiEntitiesConverter.convertToAiMessageGetDto(assistantMessage);
  }

  private void requireAccessThreadPermission(User user, AiThread aiThread) {
    Long authorId = aiThread.getUser().getUserId();
    Long userId = user.getUserId();
    if (!Objects.equals(authorId, userId) &&
        user.getRole().equals(UserRole.USER)) {
      String message = "Access to this thread is forbidden";
      log.error(message);
      throw new ForbiddenException(message);
    }
  }

  private String sendChatCompletion(List<AiMessage> messages) {
    try {
      var gptResponse = gptRestClient.post()
          .uri(YANDEX_GPT_POST_COMPLETION_URL)
          .contentType(MediaType.APPLICATION_JSON)
          .body(aiEntitiesConverter.convertToGptCompletionDto(messages))
          .retrieve()
          .body(String.class);
      return objectMapper.readTree(gptResponse)
          .get("result")
          .get("alternatives")
          .iterator()
          .next()
          .get("message")
          .get("text")
          .asText();
    } catch(HttpClientErrorException e) {
      throwInternalErrorWithMessage("Something went wrong when sending chat GPT completion request on clint");
    } catch (HttpServerErrorException e) {
      throwInternalErrorWithMessage("Something went wrong when sending chat GPT completion request on server");
    } catch (JsonProcessingException e) {
      log.error(e.getOriginalMessage());
      throwInternalErrorWithMessage("Something went wrong when parsing json");
    }
    return "";
  }

  private void throwInternalErrorWithMessage(String message) {
    log.error(message);
    throw new InternalServerErrorException(message);
  }

}
