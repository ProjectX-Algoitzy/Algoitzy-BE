package org.example.util.http_request;

import org.springframework.http.HttpHeaders;
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
      .header(HttpHeaders.USER_AGENT,
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
      .exchangeToMono(response -> response.toEntity(responseType))
      .block();
  }
}
