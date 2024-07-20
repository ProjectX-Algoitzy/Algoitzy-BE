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

    public boolean isAllowedToSendSms(String ipAddress) {
        // 해당 IP 주소에 대한 Bucket 가져오기
        Bucket bucket = buckets.computeIfAbsent(ipAddress, key -> {
            Bandwidth limit = Bandwidth.classic(MAX_REQUESTS_PER_DAY, Refill.intervally(MAX_REQUESTS_PER_DAY, Duration.ofDays(1)));
            return Bucket.builder()
                .addLimit(limit)
                .build();
        });

        // 토큰 소비 시도
        return bucket.tryConsume(1);
    }

}
