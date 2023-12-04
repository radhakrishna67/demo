package com.example.demo.model;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author balineni
 * {@code @date} 10/20/2023
 */

@Data
@ToString
@Builder
@Document("ChatShare")
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatShare {
    @Id
    private UUID shareId;
    @Indexed
    private String userId;
    @Indexed
    private String chatId;
    @Indexed
    private LocalDateTime timestamp;

}
