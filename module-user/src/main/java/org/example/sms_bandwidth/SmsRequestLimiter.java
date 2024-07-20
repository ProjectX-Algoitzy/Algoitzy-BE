package org.example.sms_bandwidth;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SmsRequestLimiter {

    private static final int MAX_REQUESTS_PER_DAY = 5;

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();



}
