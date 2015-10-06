package org.opensrp.register.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.ANCService;
import org.opensrp.register.service.AnakService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * Created by Iq on 06/10/15.
 */
@Component
public class KartuAnakImmunizationHandler implements FormSubmissionHandler {
    private AnakService anakService;

    @Autowired
    public KartuAnakImmunizationHandler(AnakService anakService) {
        this.anakService = anakService;
    }

    @Override
    public void handle(FormSubmission submission) {

        anakService.UpdateImmnunisasiBayi(submission);
    }
}
