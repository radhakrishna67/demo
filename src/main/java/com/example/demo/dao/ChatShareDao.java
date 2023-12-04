package com.example.demo.dao;

import com.example.demo.model.ChatShare;
import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author balineni
 * {@code @date} 10/9/2023
 */
@Repository("chatShareDao")
public interface ChatShareDao extends RedisDocumentRepository<ChatShare, UUID> {
}
