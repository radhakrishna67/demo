package com.example.demo.model;

import com.redis.om.spring.annotations.Indexed;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author balineni
 * {@code @date} 10/9/2023
 */
@Data
@RequiredArgsConstructor(staticName = "of")
public class Chat {
    @Indexed
    @NotEmpty(message ="chatId field value should not be null or empty")
    private UUID chatId;
    @NotEmpty(message ="chatTitle field value should not be null or empty")
    private String title;
    private List<Message> messages;
    private LocalDateTime timestamp;

}
