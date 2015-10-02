package org.opensrp.register.service;

import org.opensrp.common.AllConstants;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.domain.Child;
import org.opensrp.register.domain.Mother;
import org.opensrp.register.repository.AllChildren;
import org.opensrp.register.repository.AllMothers;
import org.opensrp.register.service.reporting.ChildReportingService;
import org.opensrp.register.service.scheduling.AnakSchedulesService;
import org.opensrp.register.service.scheduling.ChildSchedulesService;
import org.opensrp.service.formSubmission.handler.ReportFieldsDefinition;
import org.opensrp.util.SafeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.opensrp.common.AllConstants.ANCFormFields.*;
import static org.opensrp.common.AllConstants.ChildImmunizationFields.*;
import static org.opensrp.common.AllConstants.ChildRegistrationFormFields.*;
import static org.opensrp.common.AllConstants.CommonFormFields.*;
import static org.opensrp.common.AllConstants.DeliveryOutcomeFields.DELIVERY_PLACE;
import static org.opensrp.common.AllConstants.DeliveryOutcomeFields.DID_BREAST_FEEDING_START;
import static org.opensrp.common.AllConstants.PNCVisitFormFields.URINE_STOOL_PROBLEMS;
import static org.opensrp.common.AllConstants.VitaminAFields.VITAMIN_A_DOSE_PREFIX;

@Service
public class AnakService {
    public static final String IMMUNIZATIONS_SEPARATOR = " ";
    private static Logger logger = LoggerFactory.getLogger(AnakService.class.toString());
    private AnakSchedulesService anakSchedulesService;
    private AllMothers allMothers;
    private AllChildren allChildren;
    private ChildReportingService childReportingService;
    private ReportFieldsDefinition reportFieldsDefinition;

    @Autowired
    public AnakService(AnakSchedulesService anakSchedulesService,
                       AllMothers allMothers,
                       AllChildren allChildren,
                       ChildReportingService childReportingService, ReportFieldsDefinition reportFieldsDefinition) {
        this.anakSchedulesService = anakSchedulesService;
        this.allMothers = allMothers;
        this.allChildren = allChildren;
        this.childReportingService = childReportingService;
        this.reportFieldsDefinition = reportFieldsDefinition;
    }

    public void registerChildren(FormSubmission submission) {
        Mother mother = allMothers.findByCaseId(submission.entityId());
        if (mother == null) {
            logger.warn("Failed to handle children registration as there is no mother registered with id: " + submission.entityId());
            return;
        }
        String referenceDate = submission.getField(REFERENCE_DATE);
        Child child = allChildren.findByCaseId(submission.getField("childId"));
        child = child.withAnm(submission.anmId()).withDateOfBirth(referenceDate)
                .setIsClosed(false);
        allChildren.update(child);
        anakSchedulesService.enrollChild(submission.getField("childId"), referenceDate);
    }

}
