package com.tianhy.study.controller;

import com.tianhy.study.entity.Merchant;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: thy
 * @Date: 2020/2/2 23:14
 * @Desc:
 */
@RestController
public class MerchantController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("/merchant")
    public Merchant merchant(@RequestBody Merchant merchant) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, +10);
        Date delayTime = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String msg = "延迟插件测试消息，发送时间：" + sdf.format(now) + ",路由时间：" + sdf.format(delayTime);
        MessageProperties properties = new MessageProperties();
//        properties.setHeader("x-delay", delayTime.getTime() - now.getTime());
        merchant.setDesc(msg);
        merchant.setStateStr("1");
//        Message message = new Message(JSON.toJSONBytes(merchant), properties);
        System.out.println(merchant.toString());
        rabbitTemplate.convertAndSend("NICE_DIRECT_EXCHANGE", "nice.key", merchant, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay", delayTime.getTime() - now.getTime());
                return message;
            }
        });
        return merchant;
    }

}
