package com.line.backstage.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.line.backstage.service.SkuInfoService;
import com.line.backstage.service.UserInfoService;
import com.line.backstage.vo.SkuInfoVo;
import com.line.backstage.vo.UserInfoVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 项目启动时此方法先被注册成bean被spring管理,如果没有这个bean，则redis可视化工具中的中文内容（key或者value）都会以二进制存储，不易检查。
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 创建消息监听器
     *
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        List<SkuInfoVo> skuList = skuInfoService.listAll();
        if (CollectionUtils.isNotEmpty(skuList)) {
            //所有用户id通道
            for (SkuInfoVo skuInfoVo : skuList) {
                //订阅TOPIC通道
                container.addMessageListener(new MessageListener() {
                    @Override
                    public void onMessage(Message message, byte[] pattern) {
//                String msg = new String(message.getBody());
                    }
                }, new PatternTopic(skuInfoVo.getSkuCode()));

            }
        } else {
            throw new RuntimeException("商品数据通道建立失败！");
        }

        List<UserInfoVo> userList = userInfoService.queryListAll();
        if (CollectionUtils.isNotEmpty(userList)) {
            //所有用户id通道
            for (UserInfoVo userInfo : userList) {
                container.addMessageListener(new MessageListener() {
                    @Override
                    public void onMessage(Message message, byte[] pattern) {
//                    String msg = new String(message.getBody());
                    }
                }, new PatternTopic(String.valueOf(userInfo.getUserId())));
            }
        } else {
            throw new RuntimeException("用户数据通道建立失败！");
        }
        return container;
    }


}
