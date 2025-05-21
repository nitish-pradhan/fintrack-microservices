package com.fintrack.notification.service;

import com.fintrack.notification.domain.NotificationType;
import com.fintrack.notification.domain.Recipient;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

	void send(NotificationType type, Recipient recipient, String attachment) throws MessagingException, IOException;

}
