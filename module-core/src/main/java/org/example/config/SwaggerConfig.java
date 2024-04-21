package org.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI KoalaServerAPI() {
    Info info = new Info()
      .title("KOALA API")
      .description("KOALA API 명세서")
      .version("v3");

    String jwtSchemeName = "JWT TOKEN";
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
    Components components = new Components()
      .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
        .name(jwtSchemeName)
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT"));

    return new OpenAPI()
      .addServersItem(new Server().url("/"))
      .info(info)
      .addSecurityItem(securityRequirement)
      .components(components);
  }
}
