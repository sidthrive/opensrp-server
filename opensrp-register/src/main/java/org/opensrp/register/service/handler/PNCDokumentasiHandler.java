package org.opensrp.register.service.handler;

import org.opensrp.form.domain.FormSubmission;

import org.opensrp.register.service.KbService;
import org.opensrp.register.service.PNCInaService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Iq on 30/09/15.
 */
@Component
public class PNCDokumentasiHandler implements FormSubmissionHandler {
    private PNCInaService pncInaService;


    @Autowired
    public PNCDokumentasiHandler(PNCInaService pncInaService) {
        this.pncInaService = pncInaService;

    }

    @Override
    public void handle(FormSubmission submission) {
        pncInaService.dokumentasiPersalinan(submission);


    }
}
