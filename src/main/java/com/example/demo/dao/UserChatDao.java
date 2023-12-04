package com.example.demo.dao;

import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.stereotype.Repository;

/**
 * @author balineni
 * {@code @date} 10/9/2023
 */
@Repository("userChatDao")
public interface UserChatDao extends RedisDocumentRepository<com.example.demo.model.UserChat, String> {
}
