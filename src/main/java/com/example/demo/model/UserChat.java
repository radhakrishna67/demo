package com.example.demo.model;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author balineni
 * {@code @date} 9/29/2023
 */

@Data
@ToString
@Builder
@Document("UserChat")
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChat {

    @Id
    private String userId;
    @Indexed
    private List<Chat> chats;
    @Indexed
    private List<SavedMessage> savedMessages;

}
