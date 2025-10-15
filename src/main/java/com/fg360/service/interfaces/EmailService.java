package com.fg360.service.interfaces;

public interface EmailService {

    void sendEmail(String[] toUser, String subject, String message);
}
