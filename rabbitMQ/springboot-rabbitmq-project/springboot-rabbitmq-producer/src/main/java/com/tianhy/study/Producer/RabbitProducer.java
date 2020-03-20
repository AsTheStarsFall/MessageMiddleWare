package com.tianhy.study.Producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianhy.study.entity.Merchant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: thy
 * @Date: 2020/2/3 16:20
 * @Desc:
 */
@Component
public class RabbitProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send() throws JsonProcessingException {
        Merchant merchant = new Merchant();
        merchant.setId(1);
        merchant.setName("nice");
        merchant.setAddress("cn");
        merchant.setAccountName("good");
        merchant.setAccountNo("123456");
        merchant.setState("1");
        merchant.setStateStr("1");

        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(merchant);
        rabbitTemplate.convertAndSend("", "", string);

    }
}
