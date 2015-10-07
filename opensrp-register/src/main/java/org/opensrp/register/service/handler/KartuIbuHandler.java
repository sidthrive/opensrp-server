package org.opensrp.register.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.ECService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Iq on 01/10/15.
 */
@Component
public class KartuIbuHandler implements FormSubmissionHandler {
    private ECService ecService;

    @Autowired
    public KartuIbuHandler(ECService ecService) {
        this.ecService = ecService;
    }

    @Override
    public void handle(FormSubmission submission) {

        ecService.registerKartuIbu(submission);
    }
}
