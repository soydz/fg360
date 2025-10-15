package com.fg360.service.implementation;

import com.fg360.configuration.app.RabbitConfig;
import com.fg360.presentation.controller.dto.AlertDTO;
import com.fg360.presentation.controller.dto.EmailDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    @RabbitListener(queues = RabbitConfig.EMAIL_NOTIFICATION_QUEUE)
    public void listenerEmail(AlertDTO alertDTO) {
        try {
            System.out.println("Recibido mensaje en la cola 'email_notification_queue': " + alertDTO);
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
