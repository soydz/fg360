package com.fg360.configuration.app;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String NOTIFICATION_EXCHANGE = "notification_exchange";
    public static final String EMAIL_NOTIFICATION_QUEUE = "email_notification_queue";
    public static final String PUSH_NOTIFICATION_QUEUE = "push_notification_queue";
    public static final String EMAIL_SENT_ROUTING_KEY = "email.sent";
    public static final String PUSH_SENT_ROUTING_KEY = "push.sent";

    // Crea la cola, con true es durable
    @Bean
    public Queue emailNotificationQueue() {
        return new Queue(EMAIL_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Queue pushNotificationQueue() {
        return new Queue(PUSH_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding emailBinding(Queue emailNotificationQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(emailNotificationQueue)
                .to(notificationExchange)
                .with(EMAIL_SENT_ROUTING_KEY);
    }

    @Bean
    public Binding pushBinding(Queue pushNotificationQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(pushNotificationQueue)
                .to(notificationExchange)
                .with(PUSH_SENT_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}






















