package com.tianhy.study.convert;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.lang.reflect.Type;

/**
 * @Author: thy
 * @Date: 2020/2/3 11:17
 * @Desc: 自定义消息转换器
 */
public class MyMessageConvert implements MessageConverter {

    //对象转消息
    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return null;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties, Type genericType) throws MessageConversionException {
        return null;
    }

    //消息转对象
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return null;
    }
}
