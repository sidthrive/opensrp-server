package org.opensrp.register.service.handler;

import org.opensrp.form.domain.FormSubmission;

import org.opensrp.register.service.AnakService;
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
    private AnakService anakService;


    @Autowired
    public PNCDokumentasiHandler(PNCInaService pncInaService, AnakService anakService ) {
        this.pncInaService = pncInaService;
        this.anakService = anakService;
      //  this.anakService = anakService;

    }

    @Override
    public void handle(FormSubmission submission) {
        pncInaService.dokumentasiPersalinan(submission);
       anakService.registerChildren(submission);

    }
}
