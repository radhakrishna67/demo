package com.example.demo.util;


import com.example.demo.model.Chat;
import com.example.demo.model.SavedMessage;
import com.example.demo.model.UserChat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author balineni
 * {@code @date} 10/9/2023
 */
public class UserChatUtil {
    public static void setSavedMessages(UserChat userChat, SavedMessage savedMessage) {
        List<SavedMessage> savedMessages = getSavedMessages(userChat);
        if(savedMessage !=null)
            savedMessages.add(savedMessage);
        if(userChat.getSavedMessages()==null) {
            userChat.setSavedMessages(savedMessages);
        }
    }

    public static void setSavedMessages(UserChat userChat) {
        setSavedMessages(userChat, null);
    }

    public static List<SavedMessage> getSavedMessages(UserChat userChat) {
        return  Optional.ofNullable(userChat.getSavedMessages()).orElse(new ArrayList<>());
    }

    public static void setChats(UserChat userChat, Chat chat) {
        List<Chat> chats = getChats(userChat);
        chats.add(chat);
        if(userChat.getChats()==null) {
            userChat.setChats(chats);
        }
    }

    public static List<Chat> getChats(UserChat userChat) {
        return Optional.ofNullable(userChat.getChats()).orElse(new ArrayList<>());
    }

}
