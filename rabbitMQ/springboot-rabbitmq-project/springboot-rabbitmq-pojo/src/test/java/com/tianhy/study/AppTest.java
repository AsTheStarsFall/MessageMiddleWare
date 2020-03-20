package com.tianhy.study;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tianhy.study.entity.Merchant;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
//        assertTrue( true );
        Merchant merchant = new Merchant();
        merchant.setId(1);
        merchant.setName("nice");
        merchant.setAddress("cn");
        merchant.setAccountName("good");
        merchant.setAccountNo("123456");
        merchant.setState("1");
        merchant.setStateStr("1");

//        System.out.println(JSON.toJSONString(merchant));

        byte[] bytes = JSON.toJSONBytes(merchant);

        JSONObject merchant1 = JSON.parseObject(new String(bytes));
        System.out.println(merchant1.toJSONString());

    }

}
