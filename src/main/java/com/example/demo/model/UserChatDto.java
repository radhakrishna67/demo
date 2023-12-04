package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author balineni
 * {@code @date} 9/29/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChatDto {

    private UUID messageId;
    private String query;
    private String answer;
    private Boolean isSaved;
    private Boolean isRegenerate;
    private LocalDateTime timestamp;

}
