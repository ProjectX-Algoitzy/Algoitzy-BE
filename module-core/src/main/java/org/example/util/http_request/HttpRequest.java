package org.example.util.http_request;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HttpRequest {

  private static final String USER_AGENT =
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36";

  /**
   * GET method
   */
  public static <T> ResponseEntity<T> getRequest(String url, Class<T> responseType) {
    WebClient webClient = WebClient.builder()
      .baseUrl(url)
      .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
      .exchangeStrategies(
        ExchangeStrategies.builder()
          .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1))
          .build()
      )
      .build();

    return webClient
      .get()
      .exchangeToMono(response -> response.toEntity(responseType))
      .block();
  }
}
