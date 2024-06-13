package org.example.util;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtils {

  private final StringRedisTemplate redisTemplate;

  public void save(String email, String code, Duration expireTime) {
    redisTemplate.opsForValue().set(email, code, expireTime);
  }

  public void delete(String key) {
    redisTemplate.delete(key);
  }

  public String getValue(String key) {
    return redisTemplate.opsForValue().get(key);
  }

}
