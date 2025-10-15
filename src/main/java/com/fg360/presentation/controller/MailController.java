package com.fg360.presentation.controller;

import com.fg360.presentation.controller.dto.EmailDTO;
import com.fg360.service.interfaces.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class MailController {

    private final EmailService emailService;

    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> receiveReqEmail(@RequestBody EmailDTO emailDTO) {
        emailService.sendEmail(emailDTO.toUser(), emailDTO.subject(), emailDTO.message());

        Map<String, String> response = new HashMap<>();
        response.put("Estado", "Enviado");

        return ResponseEntity.ok(response);
    }
}
