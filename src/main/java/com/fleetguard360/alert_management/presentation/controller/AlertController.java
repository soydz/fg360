package com.fleetguard360.alert_management.presentation.controller;

import com.fleetguard360.alert_management.presentation.DTO.AlertDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/crear-alerta")
public class AlertController {

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchange;

    @Value("${spring.rabbitmq.routing.alert.created.key}")
    private String alertCreatedRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public AlertController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<?> createAlert(@RequestBody AlertDTO alertDTO){
        log.info("{}", alertDTO);

        rabbitTemplate.convertAndSend(exchange, alertCreatedRoutingKey, alertDTO);
        return ResponseEntity.ok().build();
    }
}
