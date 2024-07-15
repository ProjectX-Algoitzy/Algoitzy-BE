package org.example.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebDriverConfig {

  @Bean
  public WebDriver webDriver() {
    WebDriverManager.chromedriver().setup();
    return new ChromeDriver();
  }

  @Bean
  public Actions actions() {
    return new Actions(webDriver());
  }
}
