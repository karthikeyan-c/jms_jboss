package com.kc.lmsng.Entity;

import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class MyListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("Inside on message" + message);
    }
}
