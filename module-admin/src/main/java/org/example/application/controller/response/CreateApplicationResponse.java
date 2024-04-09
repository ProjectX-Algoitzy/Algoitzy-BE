package org.example.application.controller.response;

import lombok.Builder;

@Builder
public record CreateApplicationResponse(
  Long applicationId
) {

}
