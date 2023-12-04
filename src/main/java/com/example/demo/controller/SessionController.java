package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.SessionService;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author balineni
 * {@code @date} 9/28/2023
 */

@RestController
@RequestMapping("/chats")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ChatResponse createChat(@CookieValue(value = "CKPT_JSESSIONID")  String userSessionId) throws AuthenticationException {
        UserAuth userAuth = new UserAuth(userSessionId);
        return this.sessionService.createChat(userAuth);
    }

    @PostMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void saveOrUpdateChatMessage(@PathVariable("id") String chatId, @RequestBody UserChatDto message) throws AuthenticationException {
        UserAuth userAuth = new UserAuth("3853c4cf-68ea-4a93-b428-788df8ed5dc8");
        this.sessionService.saveOrUpdateChatMessage(userAuth, chatId, message);
    }

    @GetMapping( produces = APPLICATION_JSON_VALUE)
    public UserChat getAllMessagesBySessionId() throws AuthenticationException {
        UserAuth userAuth = new UserAuth("c3ab3113-b3fb-4a50-9df5-4b25f4665835");
        return this.sessionService.getAllMessagesBySessionId(userAuth);
    }


}
