package com.example.demo.dao;

import com.example.demo.model.UserChat;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author balineni
 * {@code @date} 10/9/2023
 */
public interface UserChatStreamDao {
    Optional<UserChat> getChatMessagesByUserUUIDAndChatId(Predicate<String> predicate);
}
