package com.tianhy.study.amqp.template;

import com.tianhy.study.util.ResourceUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: thy
 * @Date: 2020/2/2 20:02
 * @Desc:
 */
@Configuration
public class TemplateConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        return factory;
    }


    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        return admin;
    }

    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //交换器无法处理消息时，返回给生产者
        rabbitTemplate.setMandatory(true);
        //设置消息处理回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode,
                                        String replyText, String exchange, String routingKey) {
                System.out.println("回发的消息：\r\n");
                System.out.println("message:" + message);
                System.out.println("replyCode:" + replyText);
                System.out.println("replyText:" + replyText);
                System.out.println("exchange:" + exchange);
                System.out.println("routingKey:" + routingKey);
            }
        });
        //设置为事务模式
        rabbitTemplate.setChannelTransacted(true);
        //确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (!ack) {
                    System.err.println("message confirm callback failed");
                    throw new RuntimeException(cause);
                }
            }
        });
        return rabbitTemplate;
    }
}
