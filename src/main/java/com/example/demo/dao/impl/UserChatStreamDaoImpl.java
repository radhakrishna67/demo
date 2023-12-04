package com.example.demo.dao.impl;

import com.example.demo.dao.UserChatStreamDao;
import com.example.demo.model.UserChat;
import com.redis.om.spring.search.stream.EntityStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author balineni
 * {@code @date} 10/9/2023
 */
@Repository("userChatStreamDaoImpl")
public class UserChatStreamDaoImpl implements UserChatStreamDao {

    private static final Logger logger = LoggerFactory.getLogger(UserChatStreamDaoImpl.class);

    private final EntityStream entityStream;

    public UserChatStreamDaoImpl(EntityStream entityStream) {
        this.entityStream = entityStream;
    }

    @Override
    public Optional<UserChat> getChatMessagesByUserUUIDAndChatId(Predicate<String> predicate) {
        logger.info("getChatMessagesByUserUUIDAndChatId dao is started.");
        Optional<UserChat> userChat = entityStream
                .of(UserChat.class)
                .filter(predicate)
                .findFirst();
        logger.info("getChatMessagesByUserUUIDAndChatId dao is completed.");
        return userChat;
    }


}
