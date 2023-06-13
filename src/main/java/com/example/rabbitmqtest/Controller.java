package com.example.rabbitmqtest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class Controller {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Receiver receiver;

    @PostMapping("/default-message")
    public void produceDefaultMessage() throws InterruptedException {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMqTestApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

    @PostMapping("/message")
    public void produceMessage(@RequestBody String message) throws InterruptedException {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMqTestApplication.topicExchangeName, "foo.bar.baz", message);
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
