package sudexpert.gov.by.workproject.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String key) {
        return cache.computeIfAbsent(key, k -> Bucket.builder()
                .addLimit(Bandwidth.simple(5, Duration.ofMinutes(1))) // 5 попыток в минуту
                .build());
    }
}
