package org.opensrp.register.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.KbService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Iq on 11/09/15.
 */

@Component
public class KbFollowUpHandler implements FormSubmissionHandler {
    private KbService kbService;

    @Autowired
    public KbFollowUpHandler(KbService kbservice) {
        this.kbService = kbservice;
    }

    @Override
    public void handle(FormSubmission submission) {
        kbService.KbFollowUp(submission);
    }
}