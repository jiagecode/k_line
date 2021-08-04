//package com.line.backstage.redis;
//
//import com.line.backstage.service.TaskOrderSettlementService;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
//    private Logger logger = LoggerFactory.getLogger(RedisKeyExpirationListener.class);
//    private static final String ORDER_KEY ="order:ids:";
//
//    @Autowired
//    private TaskOrderSettlementService settlementService;
//    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
//        super(listenerContainer);
//    }
//
//    /**
//     * 针对 redis 数据失效事件，进行数据处理
//     *
//     * @param message
//     * @param pattern
//     */
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
////        String expKey = message.toString();
////        if(StringUtils.isNotEmpty(expKey)&& expKey.startsWith(ORDER_KEY)){
////            String orderStr = expKey.substring(10);
////            logger.info("开始结算编号为：{} 的订单！",orderStr);
////            settlementService.dealSettlementByOrderId(orderStr);
////        }
//    }
//}
