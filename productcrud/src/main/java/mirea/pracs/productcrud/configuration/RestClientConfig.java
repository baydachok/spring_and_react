package mirea.pracs.productcrud.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Value("${yandex.token}")
  private String yandexGptApiToken;

  @Bean
  public RestClient gptRestClient() {
    return RestClient.builder()
        .defaultHeader(
            "Authorization",
            String.format("Api-Key %s", yandexGptApiToken)
        )
        .build();
  }

}
