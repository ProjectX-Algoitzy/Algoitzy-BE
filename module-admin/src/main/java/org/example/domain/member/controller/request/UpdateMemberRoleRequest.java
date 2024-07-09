package org.example.domain.member.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.example.domain.member.enums.Role;

@Schema(description = "멤버 권한 변경 요청 객체")
public record UpdateMemberRoleRequest(

  @NotNull
  @Schema(description = "멤버 ID")
  Long memberId,

  @NotNull
  @Schema(description = "부여할 권한")
  Role role
) {

}
