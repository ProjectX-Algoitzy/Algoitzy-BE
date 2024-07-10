package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.example.validator.PhoneNumber;

@Schema(description = "아이디(이메일) 찾기 요청 객체")
public record FindEmailRequest(

  @NotBlank
  @Schema(description = "이름", type = "string")
  String name,

  @PhoneNumber
  @Schema(description = "핸드폰 번호", type = "string")
  String phoneNumber
) {

}
