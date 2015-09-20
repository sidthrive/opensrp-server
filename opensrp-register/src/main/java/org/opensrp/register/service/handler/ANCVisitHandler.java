package org.opensrp.register.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.ANCService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ANCVisitHandler implements FormSubmissionHandler {
    private ANCService ancService;

    @Autowired
    public ANCVisitHandler(ANCService ancService) {
        this.ancService = ancService;
    }

    @Override
    public void handle(FormSubmission submission) {
        ancService.ancVisit(submission);
        ancService.indonesiaTtProvided(submission);
        ancService.indonesiaHBtest(submission);
    }
}
