package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Arrays;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    private final Context context = new Context();
    private final List<String> functionality = Arrays.asList(
            "You can manage your tasks",
            "Provides connection with Trello account",
            "Application allows sending tasks to Trello"
    );

    public String buildTrelloCardEmail(String message, String shortUrl) {
        boolean containsUrl = false;
        if(!shortUrl.isEmpty()) {
            containsUrl = true;
            context.setVariable("tasks_url", shortUrl);
        }
        context.setVariable("show_button", containsUrl);
        context.setVariable("button", "check task on Trello");
        prepareContext(message);
        return templateEngine.process("/mail/created-trello-card-mail", context);
    }

    public String buildStatusEmail(String message) {
        prepareContext(message);
        return templateEngine.process("/mail/status-mail", context);
    }

    private void prepareContext(String message) {
        context.setVariable("show_button", false);
        context.setVariable("message", message);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
    }
}
