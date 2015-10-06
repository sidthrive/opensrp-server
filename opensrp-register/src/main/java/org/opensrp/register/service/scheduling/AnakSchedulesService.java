package org.opensrp.register.service.scheduling;

import org.joda.time.LocalDate;
import org.motechproject.scheduletracking.api.service.EnrollmentRecord;
import org.opensrp.register.domain.Child;
import org.opensrp.register.repository.AllChildren;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static org.joda.time.LocalDate.parse;
import static org.opensrp.common.AllConstants.ChildImmunizationFields.*;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.*;

@Service
public class AnakSchedulesService {
    private static Logger logger = LoggerFactory.getLogger(AnakSchedulesService.class.toString());

    private final AllChildren allChildren;
    private Map<String, Schedule> childSchedules;
    private HealthSchedulerService scheduler;

    @Autowired
    public AnakSchedulesService(AllChildren allChildren, HealthSchedulerService scheduler) {
        this.scheduler = scheduler;
        this.allChildren = allChildren;
       // initializeSchedules();
    }

    public void enrollChild(String childId, String referenceDate) {

        scheduler.enrollIntoSchedule(childId, "BCG POLIO",parse(referenceDate));
        scheduler.enrollIntoSchedule(childId, "CAMPAK", parse(referenceDate));
        scheduler.enrollIntoSchedule(childId,"BCG POLIO",parse(referenceDate));
        scheduler.enrollIntoSchedule(childId,"NEONATAL VISIT 1",parse(referenceDate));
        //  scheduler.enrollIntoSchedule(childId, "Measles Vaccination",parse(referenceDate));

    }

    private boolean fulfillMilestoneIfPossible(String entityId, String anmId, String scheduleName, String milestone, LocalDate fulfillmentDate) {
        if (isNotEnrolled(entityId, scheduleName)) {
            logger.warn(format("Tried to fulfill milestone {0} of {1} for entity id: {2}", milestone, scheduleName, entityId));
            return false;
        }

        logger.warn(format("Fulfilling milestone {0} of {1} for entity id: {2}", milestone, scheduleName, entityId));
        scheduler.fullfillMilestoneAndCloseAlert(entityId, anmId, scheduleName, milestone, fulfillmentDate);
        return true;
    }
    private boolean isNotEnrolled(String caseId, String scheduleName) {
        return scheduler.isNotEnrolled(caseId, scheduleName);
    }

    public void ImmunizationhasDone(String entityId, String anmId, String submissionDate) {
        if(fulfillMilestoneIfPossible(entityId,anmId,"BCG POLIO","BCG polio",parse(submissionDate))){
            scheduler.enrollIntoSchedule(entityId,"DPT1 POLIO2",parse(submissionDate));
        }
        else if(fulfillMilestoneIfPossible(entityId,anmId,"DPT1 POLIO2","DPT1 POLIO2",parse(submissionDate))){
            scheduler.enrollIntoSchedule(entityId,"DPT2 POLIO3",parse(submissionDate));
        }
        else if(fulfillMilestoneIfPossible(entityId,anmId,"DPT2 POLIO3","DPT2 POLIO3",parse(submissionDate))){
            scheduler.enrollIntoSchedule(entityId,"POLIO BOOSTER",parse(submissionDate));
        }

    }

    public void KunjunganNeonatalisDone(String entityId, String anmId, String submissionDate){
        if(fulfillMilestoneIfPossible(entityId,anmId,"NEONATAL VISIT 1","NEONATAL VISIT 1",parse(submissionDate))){
            scheduler.enrollIntoSchedule(entityId,"NEONATAL VISIT 2",parse(submissionDate));
        }
        else if(fulfillMilestoneIfPossible(entityId,anmId,"NEONATAL VISIT 2","NEONATAL VISIT 2",parse(submissionDate))){
            scheduler.enrollIntoSchedule(entityId,"NEONATAL VISIT 3",parse(submissionDate));
        }
    }

}
