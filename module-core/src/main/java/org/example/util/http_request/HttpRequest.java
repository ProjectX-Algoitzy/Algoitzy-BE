package org.example.util.http_request;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HttpRequest {

  /**
   * GET method
   */
  public static <T> ResponseEntity<T> getRequest(String url, Class<T> responseType) {
    WebClient webClient = WebClient.create(url);
    return webClient
      .get()
      .exchangeToMono(response -> response.toEntity(responseType))
      .block();
  }
}
