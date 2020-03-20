package com.tianhy.study.acl;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @Author: thy
 * @Date: 2020/2/7 11:20
 * @Desc: 访问控制-生产者
 * 需要配置：  rocketmq.producer.access-key=AK
 * rocketmq.producer.secret-key=SK
 */
@Component
public class AclProducer {
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    public SendResult send(String msg) {
        SendResult sendResult = rocketMQTemplate.syncSend("SPRING_BOOT_TOPIC", msg);
        return sendResult;
    }


    //如果是事务消息，需要在注解中配置属性：  accessKey = "AK", secretKey = "SK"
    @RocketMQTransactionListener(txProducerGroup = "TRANS_PRODUCER_GROUP", accessKey = "AK", secretKey = "SK")
    class TransListener implements RocketMQLocalTransactionListener {

        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
            return RocketMQLocalTransactionState.COMMIT;
        }
    }
}
