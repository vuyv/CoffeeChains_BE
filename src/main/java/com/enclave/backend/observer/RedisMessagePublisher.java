package com.enclave.backend.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisMessagePublisher {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ChannelTopic topic;

    public void publish(List<Short> list){
        redisTemplate.convertAndSend(topic.getTopic(), list);

    }
}
