package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private static final String SUBJECT = "Tasks: Once a day email";

    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;

    //once a day
    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String messageEnding;
        if (size == 1) {
            messageEnding = " task.";
        } else {
            messageEnding = " tasks.";
        }
        simpleEmailService.send(new Mail(adminConfig.getAdminMail(),
                SUBJECT,
                "Currently in database you got: " +  size + messageEnding));
    }
}
