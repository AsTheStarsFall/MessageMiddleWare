package com.tianhy.study.amqp.template;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: thy
 * @Date: 2020/2/2 19:47
 * @Desc:
 */
public class TemplateSender {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TemplateSender.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        //设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if(ack){
                    System.out.println("消息确认成功");
                }else {
                    System.out.println("消息未确认");
                }
            }
        });

        rabbitTemplate.convertAndSend("TEMPLATE_EXCHANGE", "TEMPLATE_KEY", "template msg");

    }
}
