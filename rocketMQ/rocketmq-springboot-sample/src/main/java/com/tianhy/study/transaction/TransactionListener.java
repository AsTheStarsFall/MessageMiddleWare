package com.tianhy.study.transaction;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @Author: thy
 * @Date: 2020/2/7 10:43
 * @Desc: 事务消息回查
 */
@Component
@RocketMQTransactionListener(txProducerGroup = "TRANS_PRODUCER_GROUP")
public class TransactionListener implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        //事务执行进程
        /**
         *     三种状态：
         *     COMMIT,
         *     ROLLBACK,
         *     UNKNOWN
         */
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        //检查事务状态
        return RocketMQLocalTransactionState.COMMIT;
    }
}
