package org.opensrp.register.service;

/**
 * Created by soran on 19/08/16.
 */
import org.opensrp.common.AllConstants;
import org.opensrp.common.util.IntegerUtil;
import org.opensrp.register.domain.Child;
import org.opensrp.register.domain.Mother;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.repository.AllChildren;
import org.opensrp.register.service.reporting.ChildReportingService;
import org.opensrp.register.service.scheduling.AnakSchedulesService;
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
public class NutritionistService {

    public static final String IMMUNIZATIONS_SEPARATOR = " ";
    private static Logger logger = LoggerFactory.getLogger(AnakService.class.toString());
    private AnakSchedulesService anakSchedulesService;
    private AllMothers allMothers;
    private AllChildren allChildren;
    private ChildReportingService childReportingService;
    private ReportFieldsDefinition reportFieldsDefinition;

    @Autowired
    public NutritionistService(AnakSchedulesService anakSchedulesService,
                       AllMothers allMothers,
                       AllChildren allChildren,
                       ChildReportingService childReportingService, ReportFieldsDefinition reportFieldsDefinition) {
        this.anakSchedulesService = anakSchedulesService;
        this.allMothers = allMothers;
        this.allChildren = allChildren;
        this.childReportingService = childReportingService;
        this.reportFieldsDefinition = reportFieldsDefinition;
    }

    public void GiziVisit(FormSubmission submission) {
        Child child = allChildren.findByCaseId(submission.entityId());
        if (child == null) {
            logger.warn("Found immunization update without registered child for entity ID: " + submission.entityId());
            return;
        }
        anakSchedulesService.visithasdone(submission.entityId(),
                submission.anmId(),
                submission.getField("tanggalPenimbangan"));

        //   anakSchedulesService.updateEnrollments(submission.entityId(), );
    }

}

