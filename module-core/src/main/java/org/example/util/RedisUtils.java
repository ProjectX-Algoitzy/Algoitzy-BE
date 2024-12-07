package org.example.util;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
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

  public void delete(Set<String> keySet) {
    redisTemplate.delete(keySet);
  }

  public String getValue(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public Set<String> findAllKeysByPattern(String keyPattern) {
    return redisTemplate.keys(keyPattern);
  }

  public void addViewCount(String key) {
    DefaultRedisScript<Void> redisScript = new DefaultRedisScript<>();
    redisScript.setLocation(new ClassPathResource("/lua/addViewCount.lua"));
    redisScript.setResultType(Void.class);
    redisTemplate.execute(redisScript, Collections.singletonList(key));
  }

}
