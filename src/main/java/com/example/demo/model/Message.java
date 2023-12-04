package com.example.demo.model;

import com.redis.om.spring.annotations.Indexed;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author balineni
 * {@code @date} 10/9/2023
 */

@Data
@RequiredArgsConstructor(staticName = "of")
public class Message {
    @Indexed
    private UUID messageId;
    @Indexed
    private String query;
    private String answer;
    @Indexed
    private Boolean isSaved;
    @Indexed
    private Boolean isRegenerate;
    @Indexed
    private LocalDateTime timestamp;
}
