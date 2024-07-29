package org.example.util;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtils {

  private final StringRedisTemplate redisTemplate;

  public void save(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  public void saveWithExpireTime(String key, String value, Duration expireTime) {
    redisTemplate.opsForValue().set(key, value, expireTime);
  }

  public void delete(String key) {
    redisTemplate.delete(key);
  }

  public String getValue(String key) {
    return redisTemplate.opsForValue().get(key);
  }

}
