package org.opensrp.register.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.KbService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KbHandler implements FormSubmissionHandler {
    private KbService kbService;

    @Autowired
    public KbHandler(KbService kbservice) {
        this.kbService = kbservice;
    }

    @Override
    public void handle(FormSubmission submission) {
            kbService.KbInjection(submission);
    }
}
