package org.opensrp.register.service;

import org.opensrp.common.AllConstants;
import org.opensrp.common.util.IntegerUtil;
import org.opensrp.register.domain.Mother;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.scheduling.KbSchedulesService;
import org.opensrp.util.SafeMap;
import org.opensrp.register.repository.AllEligibleCouples;
import org.opensrp.register.repository.AllMothers;
import org.opensrp.service.formSubmission.handler.ReportFieldsDefinition;
import org.opensrp.register.service.reporting.MotherReportingService;
import org.opensrp.register.service.reporting.rules.IsHypertensionDetectedRule;
import org.opensrp.register.service.scheduling.ANCSchedulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.ANCCloseFields.DEATH_OF_WOMAN_VALUE;
import static org.opensrp.common.AllConstants.ANCCloseFields.PERMANENT_RELOCATION_VALUE;
import static org.opensrp.common.AllConstants.ANCFormFields.*;
import static org.opensrp.common.AllConstants.ANCInvestigationsFormFields.*;
import static org.opensrp.common.AllConstants.ANCVisitFormFields.*;
import static org.opensrp.common.AllConstants.BOOLEAN_FALSE_VALUE;
import static org.opensrp.common.AllConstants.BOOLEAN_TRUE_VALUE;
import static org.opensrp.common.AllConstants.CommonFormFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.CommonFormFields.SUBMISSION_DATE_FIELD_NAME;
import static org.opensrp.common.AllConstants.EntityCloseFormFields.CLOSE_REASON_FIELD_NAME;
import static org.opensrp.common.AllConstants.HbTestFormFields.*;
import static org.opensrp.common.AllConstants.IFAFields.IFA_TABLETS_DATE;
import static org.opensrp.common.AllConstants.IFAFields.NUMBER_OF_IFA_TABLETS_GIVEN;
import static org.opensrp.common.util.EasyMap.create;
import static org.joda.time.LocalDate.parse;

@Service
public class KbService {

    private static Logger logger = LoggerFactory.getLogger(KbService.class.toString());

    private AllMothers allMothers;
    private AllEligibleCouples eligibleCouples;
    private KbSchedulesService kbSchedulesService;
    private MotherReportingService reportingService;
    private ReportFieldsDefinition reportFieldsDefinition;

    @Autowired
    public KbService(AllMothers allMothers,
                      AllEligibleCouples eligibleCouples,
                      KbSchedulesService kbSchedulesService,
                      MotherReportingService reportingService,
                      ReportFieldsDefinition reportFieldsDefinition) {
        this.allMothers = allMothers;
        this.eligibleCouples = eligibleCouples;
        this.kbSchedulesService = kbSchedulesService;
        this.reportingService = reportingService;
        this.reportFieldsDefinition = reportFieldsDefinition;
    }

    public void registerKB(FormSubmission submission) {
        String motherId = submission.getField(AllConstants.ANCFormFields.ID_IBU);

        if (!eligibleCouples.exists(submission.entityId())) {
            logger.warn(format("Found mother without registered eligible couple. Ignoring: {0} for mother with id: {1} for ANM: {2}",
                    submission.entityId(), motherId, submission.anmId()));
            return;
        }

        Mother mother = allMothers.findByCaseId(motherId);
        allMothers.update(mother.withAnm(submission.anmId()));

//        KbInjection(submission);

//        List<String> reportFields = reportFieldsDefinition.get(submission.formName());
//        reportingService.registerANC(new SafeMap(submission.getFields(reportFields)));
    }

    public void KbInjection(FormSubmission submission) {
        String motherId = submission.getField(AllConstants.ANCFormFields.MOTHER_ID);

        Mother mother = allMothers.findByCaseId(motherId);

    //    ancSchedulesService.enrollMother(motherId, parse(submission.getField(REFERENCE_DATE)));

//        List<String> reportFields = reportFieldsDefinition.get(submission.formName());
        if (mother == null) {
            logger.warn("Tried to handle KB provided without registered mother. Submission: " + submission);
            return;
        }
        String jenisKontrasepsiYangDigunakan = submission.getField("jenisKontrasepsi");
        kbSchedulesService.kbHasHappen(submission.entityId(),
                submission.anmId(),
                jenisKontrasepsiYangDigunakan,
                submission.getField("tanggalkunjungan"));
    }

    public void KbFollowUp(FormSubmission submission) {
        String motherId = submission.getField(AllConstants.ANCFormFields.MOTHER_ID);

        Mother mother = allMothers.findByCaseId(motherId);

        //    ancSchedulesService.enrollMother(motherId, parse(submission.getField(REFERENCE_DATE)));

//        List<String> reportFields = reportFieldsDefinition.get(submission.formName());
        if (mother == null) {
            logger.warn("Tried to handle KB provided without registered mother. Submission: " + submission);
            return;
        }
        String jenisKontrasepsiYangDigunakan = submission.getField("jenisKontrasepsi");
        kbSchedulesService.kbFollowUpHasHappen(submission.entityId(),
                submission.anmId(),
                jenisKontrasepsiYangDigunakan,
                submission.getField("tanggalkunjungan"));

    }
}

