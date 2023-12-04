package com.example.demo.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author balineni
 * {@code @date} 10/9/2023
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private UUID id;
    private String title;
    private List<Message> messages;
    private LocalDateTime timestamp;

}
