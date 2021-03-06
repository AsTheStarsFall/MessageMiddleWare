package com.tianhy.study.amqp.admin;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: thy
 * @Date: 2020/2/2 18:50
 * @Desc:
 */
@ComponentScan(basePackages = "com.tianhy.study.amqp")
public class AdminTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AdminTest.class);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);

        //声明一个交换器
        rabbitAdmin.declareExchange(new DirectExchange("ADMIN_EXCHANGE"));
        //声明一个队列
        rabbitAdmin.declareQueue(new Queue("ADMIN_QUEUE"));
        /**
         * String destination, DestinationType destinationType, String exchange, String routingKey,Map<String, Object> arguments
         */
        rabbitAdmin.declareBinding(new Binding("ADMIN_QUEUE", Binding.DestinationType.QUEUE,
                "ADMIN_EXCHANGE", "admin", null));
    }
}
