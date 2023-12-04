package com.example.demo.model;

import lombok.*;

/**
 * @author balineni
 * {@code @date} 4/4/2023
 */
@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
public class UserAuth {
    private String sessionId;
}
