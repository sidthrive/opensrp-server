package org.opensrp.register.service;

import org.opensrp.common.AllConstants;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.domain.Mother;
import org.opensrp.register.repository.AllEligibleCouples;
import org.opensrp.register.repository.AllMothers;
import org.opensrp.register.service.reporting.MotherReportingService;
import org.opensrp.register.service.scheduling.KbSchedulesService;
import org.opensrp.register.service.scheduling.PncInaScheduleService;
import org.opensrp.service.formSubmission.handler.ReportFieldsDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.CommonFormFields.REFERENCE_DATE;
/**
 * Created by Iq on 02/10/15.
 */
@Service
public class PNCInaService {

    private static Logger logger = LoggerFactory.getLogger(PNCInaService.class.toString());

    private AllMothers allMothers;
    private AllEligibleCouples eligibleCouples;
    private PncInaScheduleService pncInaScheduleService;
    private MotherReportingService reportingService;
    private ReportFieldsDefinition reportFieldsDefinition;

    @Autowired
    public PNCInaService(AllMothers allMothers,
                         AllEligibleCouples eligibleCouples,
                         KbSchedulesService kbSchedulesService,
                         PncInaScheduleService pncInaScheduleService,
                         MotherReportingService reportingService,
                         ReportFieldsDefinition reportFieldsDefinition) {
        this.allMothers = allMothers;
        this.eligibleCouples = eligibleCouples;
        this.pncInaScheduleService = pncInaScheduleService;
        this.reportingService = reportingService;
        this.reportFieldsDefinition = reportFieldsDefinition;
    }

    public void dokumentasiPersalinan(FormSubmission submission) {
        Mother mother = allMothers.findByCaseId(submission.entityId());
        String keadaanIbu = submission.getField("keadaanIbu");
        String KeadaanBayi = submission.getField("keadaanBayi");
        if (mother == null) {
            logger.warn(format("Failed to handle delivery outcome as there is no mother registered with ID: {0}", submission.entityId()));
            return;
        }
        if ("mati".equalsIgnoreCase(keadaanIbu)) {
            logger.info("Closing Mother as the mother died during delivery. Mother Id: " + mother.caseId());
            mother.setIsClosed(true);
            //unenroll
            pncInaScheduleService.unEnrollFromSchedules(submission.entityId());

        } else if ("hidup".equalsIgnoreCase(keadaanIbu)) {
            pncInaScheduleService.PersalinanisDone(submission.entityId(), submission.getField(REFERENCE_DATE));
        }

    }
}