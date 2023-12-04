package com.example.demo.service;

import com.example.demo.model.*;

import javax.security.sasl.AuthenticationException;

/**
 * @author balineni
 * {@code @date} 9/29/2023
 */
public interface SessionService {
    void saveOrUpdateChatMessage(UserAuth userAuth, String chatId, UserChatDto message) throws AuthenticationException;

    Chat getChatMessages(UserAuth userAuth, String chatId) throws AuthenticationException;

    UserChat getAllMessagesBySessionId(UserAuth userAuth) throws AuthenticationException;

    ChatResponse createChat(UserAuth userAuth) throws AuthenticationException;


}
