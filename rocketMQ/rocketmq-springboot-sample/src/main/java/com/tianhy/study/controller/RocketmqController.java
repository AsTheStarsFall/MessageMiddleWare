package com.tianhy.study.controller;

import com.tianhy.study.example.RocketmqProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: thy
 * @Date: 2020/2/6 17:18
 * @Desc:
 */
@RestController
public class RocketmqController {

    @Autowired
    RocketmqProducer rocketmqProducer;

    @GetMapping("/hello")
    public SendResult hello(@RequestParam String msg) {
        SendResult sendResult = rocketmqProducer.send(msg);
        return sendResult;
    }
}
