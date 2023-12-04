package com.example.demo.service.impl;


import com.example.demo.dao.ChatShareDao;
import com.example.demo.dao.UserChatDao;
import com.example.demo.dao.UserChatStreamDao;
import com.example.demo.model.*;
import com.example.demo.service.SessionService;
import com.example.demo.service.UUIDGenerator;
import com.example.demo.util.UserChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * @author balineni
 * {@code @date} 9/29/2023
 */
@Service("sessionServiceImpl")
public class SessionServiceImpl implements SessionService {

    private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);


    private final UserChatDao userChatDao;

    private final UserChatStreamDao chatStreamDao;

    private final ChatShareDao chatShareDao;
    private final UUIDGenerator uuidGenerator;


    @Value("${ghostwriter.chat.share.url}")
    private String SHARE_URL;

    public SessionServiceImpl( UserChatDao userChatDao, UserChatStreamDao chatStreamDao, ChatShareDao chatShareDao, UUIDGenerator uuidGenerator) {
        this.userChatDao = userChatDao;
        this.chatStreamDao = chatStreamDao;
        this.chatShareDao = chatShareDao;
        this.uuidGenerator = uuidGenerator;
    }

    @Override
    public void saveOrUpdateChatMessage(UserAuth userAuth, String chatId, UserChatDto chatDto) throws AuthenticationException {
        logger.info("saveChatMessage service is started.");
        User user = new User();

        Optional<UserChat> chatOptional = userChatDao.findById("00uqgwatgxIFc86w0357");

        UserChat userChat = chatOptional.orElseGet(UserChat::of);

        // If the logged-in user is not present in the database
        if (chatOptional.isEmpty()) {
            userChat.setUserId(user.getUuid());
        }

        // If the given user is present in the database
        Message message = Message.of();
        message.setMessageId(chatDto.getMessageId() == null ? uuidGenerator.generateUUID() : chatDto.getMessageId());
        message.setQuery(chatDto.getQuery());
        message.setAnswer(chatDto.getAnswer());
        message.setIsSaved(chatDto.getIsSaved());
        message.setIsRegenerate(chatDto.getIsRegenerate());
        message.setTimestamp(chatDto.getTimestamp() == null ? LocalDateTime.now() : chatDto.getTimestamp());

        SavedMessage savedMessage = SavedMessage.of();
        savedMessage.setChatId(chatId);
        savedMessage.setMessageId(chatDto.getMessageId() == null ? message.getMessageId() : chatDto.getMessageId());
        savedMessage.setQuery(chatDto.getQuery());
        savedMessage.setAnswer(chatDto.getAnswer());
        savedMessage.setIsSaved(chatDto.getIsSaved());
        savedMessage.setIsRegenerate(chatDto.getIsRegenerate());
        savedMessage.setTimestamp(chatDto.getTimestamp()==null ? message.getTimestamp() : chatDto.getTimestamp());

        if (chatOptional.isPresent()) {

            List<SavedMessage> savedMessages = UserChatUtil.getSavedMessages(userChat);
            Optional<SavedMessage> optionalSavedMessage = savedMessages
                    .stream()
                    .filter(iSavedMessage -> chatId.equalsIgnoreCase(iSavedMessage.getChatId()) && chatDto.getMessageId().equals(iSavedMessage.getMessageId()))
                    .findFirst();

            List<Chat> chats = UserChatUtil.getChats(userChat);

            Optional<Chat> foundChat = chats
                    .stream()
                    .filter(chat -> chatId.equalsIgnoreCase(chat.getChatId().toString()))
                    .findFirst();

                foundChat

                        .ifPresent(chat -> chat.getMessages()
                                .stream()
                                .filter(imessage -> imessage.getMessageId().equals(chatDto.getMessageId()))
                                .findFirst()
                                .ifPresentOrElse(imessage -> {
                                            imessage.setIsSaved(chatDto.getIsSaved());
                                            imessage.setIsRegenerate(chatDto.getIsRegenerate());
                                        },
                                        () -> chat.getMessages().add(message)
                                )
                        );

            if(foundChat.isEmpty()){
                Chat chat = Chat.of();
                chat.setChatId(UUID.fromString(chatId));
                chat.setTitle(getChatTitle());
                chat.setMessages(List.of(message));
                chat.setTimestamp(LocalDateTime.now());
                UserChatUtil.setChats(userChat, chat);
            }

                if (chatDto.getIsSaved()) {
                    optionalSavedMessage
                            .ifPresentOrElse(iSavedMessage -> {
                                        iSavedMessage.setIsSaved(chatDto.getIsSaved());
                                        iSavedMessage.setIsRegenerate(chatDto.getIsRegenerate());
                                        iSavedMessage.setTimestamp(chatDto.getTimestamp());
                                    },
                                    () -> UserChatUtil.setSavedMessages(userChat, savedMessage)
                            );
                } else if (!chatDto.getIsRegenerate()) {
                    List<SavedMessage> updatedSavedMsgs = savedMessages
                            .stream()
                            .filter(iSavedMessage -> !chatDto.getMessageId().equals(iSavedMessage.getMessageId()))
                            .collect(Collectors.toList());
                    userChat.setSavedMessages(updatedSavedMsgs);
                }

        } else {
            if (chatDto.getIsSaved()) {
                UserChatUtil.setSavedMessages(userChat, savedMessage);
            }

            Chat chat = Chat.of();
            chat.setChatId(UUID.fromString(chatId));
            chat.setTitle(getChatTitle());
            chat.setMessages(List.of(message));
            chat.setTimestamp(LocalDateTime.now());
            UserChatUtil.setChats(userChat, chat);
            if(userChat.getSavedMessages()==null)
                userChat.setSavedMessages(List.of());
        }


        saveUserChat(userChat, "Exception while saving chat messages for the user {}");
        logger.info("saveChatMessage service is completed.");
    }

    @Override
    public UserChat getAllMessagesBySessionId(UserAuth userAuth) {
        logger.info("getAllMessagesBySessionId service is started.");
        User user = new User();
        user.setUuid("00uqgwatgxIFc86w0357");

        Optional<UserChat> chatOptional = userChatDao.findById("00uqgwatgxIFc86w0357");


        Predicate<String> predicate = UserChat$.USER_ID.eq("00uqgwatgxIFc86w0357");
        UserChat userChat = null;

        try {
            Optional<UserChat> userChatsOptional = chatStreamDao.getChatMessagesByUserUUIDAndChatId(predicate);
            if (userChatsOptional.isPresent()) {
                userChat = userChatsOptional.get();
            }
        } catch (Exception e) {
            logger.info("Exception while getting chat messages for the user {}", e.getMessage());
            throw e;
        }

        // If the logged-in user is not present in the database, create an empty chat record
        if (userChat == null) {
            userChat = UserChat.of();
            userChat.setUserId(user.getUuid());
            createAndSaveNewUserChat(userChat);
        }

        if(userChat.getSavedMessages()==null) {
            userChat.setSavedMessages(List.of());
        }
        logger.info("getAllMessagesBySessionId service is completed.");
        return userChat;
    }

    @Override
    public Chat getChatMessages(UserAuth userAuth, String chatId) throws AuthenticationException {
        logger.info("getChatMessages service is started.");
        User user = new User();

        Chat chat = getChatMessagesByChatId(chatId, user);
        logger.info("getChatMessages service is completed.");
        return chat;
    }

    private Chat getChatMessagesByChatId(String chatId, User user) {
        Predicate<String> predicate = null; //UserChat$.USER_ID.eq(user.getUuid());
        Optional<UserChat> userChatsOptional = chatStreamDao.getChatMessagesByUserUUIDAndChatId(predicate);

        Chat chat = Chat.of();
        try {
            if (userChatsOptional.isPresent()) {
                UserChat userChat = userChatsOptional.get();
                chat = userChat.getChats().stream().filter(chat1 -> chatId.equalsIgnoreCase(chat1.getChatId().toString())).findFirst().orElse(Chat.of());
            }
        } catch (Exception e) {
            logger.info("Exception while getting chat messages for the user {}", e.getMessage());
            throw e;
        }
        return chat;
    }

    @Override
    public ChatResponse createChat(UserAuth userAuth) throws AuthenticationException {
        logger.info("createChat service is started.");
        User user = new User();


        Optional<UserChat> chatOptional = userChatDao.findById(user.getUuid());

        UserChat userChat = chatOptional.orElseGet(UserChat::of);

        if(chatOptional.isEmpty()) {
            userChat.setUserId(user.getUuid());
        }

        Chat chat = createAndSaveNewUserChat(userChat);

        ChatResponse chatResponse = ChatResponse.builder()
                .id(chat.getChatId())
                .title(getChatTitle())
                .messages(chat.getMessages())
                .timestamp(chat.getTimestamp())
                .build();

        logger.info("createChat service is completed.");
        return chatResponse;
    }




    private Chat createAndSaveNewUserChat(UserChat userChat) {
        Chat chat = getChat();

        UserChatUtil.setChats(userChat, chat);
        UserChatUtil.setSavedMessages(userChat);

        saveUserChat(userChat, "Exception while creating a new chat for the user {}");
        return chat;
    }

    private Chat getChat() {
        Chat chat = Chat.of();
        chat.setChatId(uuidGenerator.generateUUID());
        chat.setTitle(getChatTitle());
        chat.setMessages(List.of());
        chat.setTimestamp(LocalDateTime.now());
        return chat;
    }

    private static String getChatTitle() {
        return getLocalDateTimeWithoutMillis(LocalDateTime.now());
    }

    private static String getLocalDateTimeWithoutMillis(LocalDateTime now) {
        if(now==null)
            now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private void saveUserChat(UserChat userChat, String format) {
        try {
            userChatDao.save(userChat);
        } catch (Exception e) {
            logger.info(format, e.getMessage());
            throw e;
        }
    }

}
