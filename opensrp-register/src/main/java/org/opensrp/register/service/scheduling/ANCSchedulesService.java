package org.opensrp.register.service.scheduling;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.joda.time.LocalDate.parse;
import static org.opensrp.common.util.IntegerUtil.tryParse;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.*;

import org.joda.time.LocalDate;
import org.joda.time.Weeks;
import org.motechproject.scheduletracking.api.service.EnrollmentRecord;
import org.opensrp.common.AllConstants;
import org.opensrp.common.util.DateUtil;
import org.opensrp.scheduler.HealthSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ANCSchedulesService {
    public static final int NUMBER_OF_WEEKS_BEFORE_HB_TEST_2_BECOMES_DUE = 28;
    private static Logger logger = LoggerFactory.getLogger(ANCSchedulesService.class.toString());

    private static final String[] NON_ANC_SCHEDULES = {SCHEDULE_EDD, SCHEDULE_LAB,  SCHEDULE_TT_1, SCHEDULE_IFA_1,
            SCHEDULE_HB_TEST_1,  SCHEDULE_DELIVERY_PLAN};
    private HealthSchedulerService scheduler;

    @Autowired
    public ANCSchedulesService(HealthSchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void enrollMother(String caseId, LocalDate referenceDateForSchedule) {
        for (String schedule : NON_ANC_SCHEDULES) {
        	scheduler.enrollIntoSchedule(caseId, schedule, referenceDateForSchedule);
        }
        enrollIntoCorrectMilestoneOfANCCare(caseId, referenceDateForSchedule);
    }

    public void ancVisitHasHappened(String entityId, String anmId, int visitNumberToFulfill, String visitDate) {
        fastForwardSchedule(entityId, anmId, SCHEDULE_ANC, SCHEDULE_ANC_MILESTONE_PREFIX, visitNumberToFulfill, parse(visitDate));
    }

    public void deliveryHasBeenPlanned(String entityId, String anmId, String deliveryPlanDate) {
        fulfillMilestoneIfPossible(entityId, anmId, SCHEDULE_DELIVERY_PLAN, SCHEDULE_DELIVERY_PLAN, parse(deliveryPlanDate));
    }

    public void ttVisitHasHappened(String entityId, String anmId, String ttDose, String ttDate) {
        if (AllConstants.ANCFormFields.TT_BOOSTER_DOSE_VALUE.equals(ttDose)) {
            unEnrollFromSchedule(entityId, anmId, SCHEDULE_TT_1);
        } else if (AllConstants.ANCFormFields.TT1_DOSE_VALUE.equals(ttDose)) {
            fulfillMilestoneIfPossible(entityId, anmId, SCHEDULE_TT_1, SCHEDULE_TT_1, parse(ttDate));
            scheduler.enrollIntoSchedule(entityId, SCHEDULE_TT_2, ttDate);
        } else if (AllConstants.ANCFormFields.TT2_DOSE_VALUE.equals(ttDose)) {
            fulfillMilestoneIfPossible(entityId, anmId, SCHEDULE_TT_2, SCHEDULE_TT_2, parse(ttDate));
        }
    }

    public void InaTtVisitHasHappened(String entityId, String anmId, String ttVisit, String date){
        if("tt_ke_1".equalsIgnoreCase(ttVisit)){
            scheduler.enrollIntoSchedule(entityId, "TT KE 1", date);
        }else if("tt_ke_2".equalsIgnoreCase(ttVisit)){
            fulfillMilestoneIfPossible(entityId,anmId,"TT KE 1","TT KE 1",parse(date));
            scheduler.enrollIntoSchedule(entityId,"TT KE 2", date);
        }
    }

    public void ifaTabletsGiven(String entityId, String anmId, String numberOfIFATabletsGiven, String ifaGivenDate) {
        if (tryParse(numberOfIFATabletsGiven, 0) <= 0) {
            logger.info("Number of IFA tablets given is zero so not updating schedules for entity: " + entityId);
            return;
        }
        if (fulfillMilestoneIfPossible(entityId, anmId, SCHEDULE_IFA_1, SCHEDULE_IFA_1, parse(ifaGivenDate))) {
            logger.info("Enrolling ANC to IFA 2 schedule. Entity id: " + entityId);

            scheduler.enrollIntoSchedule(entityId, SCHEDULE_IFA_2, ifaGivenDate);
            return;
        }
        if (fulfillMilestoneIfPossible(entityId, anmId, SCHEDULE_IFA_2, SCHEDULE_IFA_2, parse(ifaGivenDate))) {
            logger.info("Enrolling ANC to IFA 3 schedule. Entity id: " + entityId);

            scheduler.enrollIntoSchedule(entityId, SCHEDULE_IFA_3, ifaGivenDate);
            return;
        }
        fulfillMilestoneIfPossible(entityId, anmId, SCHEDULE_IFA_3, SCHEDULE_IFA_3, parse(ifaGivenDate));
    }

    public void hbTestDone(String entityId, String anmId, String date, String anaemicStatus, LocalDate lmp) {
        if (fulfillMilestoneIfPossible(entityId, anmId, SCHEDULE_HB_TEST_1, SCHEDULE_HB_TEST_1, parse(date))) {
            if (isNotBlank(anaemicStatus)) {
                logger.info(format("ANC is anaemic so enrolling her to Hb Followup Test schedule: Entity id:{0}, Anaemic status: {1}", entityId, anaemicStatus));
                scheduler.enrollIntoSchedule(entityId, SCHEDULE_HB_FOLLOWUP_TEST, date);
            } else {
                enrollANCToHbTest2Schedule(entityId, lmp);
            }
        } else if (fulfillMilestoneIfPossible(entityId, anmId, SCHEDULE_HB_FOLLOWUP_TEST, SCHEDULE_HB_FOLLOWUP_TEST, parse(date))) {
            if (parse(date).isAfter(lmp.plusWeeks(NUMBER_OF_WEEKS_BEFORE_HB_TEST_2_BECOMES_DUE))) {
                fulfillMilestoneIfPossible(entityId, anmId, SCHEDULE_HB_TEST_2, SCHEDULE_HB_TEST_2, parse(date));
            } else {
                enrollANCToHbTest2Schedule(entityId, lmp);
            }
        } else {
            fulfillMilestoneIfPossible(entityId, anmId, SCHEDULE_HB_TEST_2, SCHEDULE_HB_TEST_2, parse(date));
        }
    }

    private void enrollANCToHbTest2Schedule(String entityId, LocalDate lmp) {
        logger.info(format("Enrolling ANC to Hb Test 2 schedule: {0} Entity id:{1}", SCHEDULE_HB_TEST_2, entityId));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_HB_TEST_2, lmp.toString());
    }

    public void forceFulfillMilestone(String externalId, String scheduleName) {
        // TODO SHOULD DO SOMETHING
    	// trackingService.fulfillCurrentMilestone(externalId, scheduleName, today(), new Time(now()));
    }

    public void unEnrollFromAllSchedules(String entityId) {
        scheduler.unEnrollFromAllSchedules(entityId);
    }

    private void unEnrollFromSchedule(String entityId, String anmId, String scheduleName) {
        logger.info(format("Un-enrolling ANC with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollFromSchedule(entityId, anmId, scheduleName);
    }

    private void enrollIntoCorrectMilestoneOfANCCare(String entityId, LocalDate referenceDateForSchedule) {
        String milestone;

        if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(14).toPeriod().minusDays(1))) {
            milestone = SCHEDULE_ANC_1;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(28).toPeriod().minusDays(1))) {
            milestone = SCHEDULE_ANC_2;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(36).toPeriod().minusDays(1))) {
            milestone = SCHEDULE_ANC_3;
        } else {
            milestone = SCHEDULE_ANC_4;
        }

        logger.info(format("Enrolling ANC with Entity id:{0} to ANC schedule, milestone: {1}.", entityId, milestone));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_ANC, milestone, referenceDateForSchedule.toString());
    }

    private void fastForwardSchedule(String entityId, String anmId, String scheduleName, String milestonePrefix, int visitNumberToFulfill, LocalDate visitDate) {
        int currentMilestoneNumber = currentMilestoneNumber(entityId, scheduleName, milestonePrefix);
        for (int i = currentMilestoneNumber; i <= visitNumberToFulfill; i++) {
            fulfillMilestoneIfPossible(entityId, anmId, scheduleName, milestonePrefix + " " + i, visitDate);
        }
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

    private int currentMilestoneNumber(String caseId, String scheduleName, String milestonePrefix) {
        if (isNotEnrolled(caseId, scheduleName)) {
            return Integer.MAX_VALUE;
        }

        EnrollmentRecord record = scheduler.getEnrollment(caseId, scheduleName);
        return Integer.valueOf(record.getCurrentMilestoneName().replace(milestonePrefix + " ", ""));
    }

    private boolean isNotEnrolled(String caseId, String scheduleName) {
        return scheduler.isNotEnrolled(caseId, scheduleName);
    }
    //indonesian version
    public void hbTestRegistrationDone(String entityId, String anmId, String laboratoriumPeriksaHbDilakukan,String date, String laboratoriumPeriksaHbAnemia) {
        if("ya".equalsIgnoreCase(laboratoriumPeriksaHbDilakukan)) {
            if ("positif".equalsIgnoreCase(laboratoriumPeriksaHbAnemia)) {
                scheduler.enrollIntoSchedule(entityId, SCHEDULE_INA_HB_FOLLOW, parse(date));
            } else {
                scheduler.enrollIntoSchedule(entityId, SCHEDULE_INA_HB_1, parse(date));
            }
        }else{
            scheduler.enrollIntoSchedule(entityId,SCHEDULE_INA_HB_1,parse(date));
        }

    }
    //indonesian version
    public void hbTestVisitDone(String entityId, String anmId, String laboratoriumPeriksaHbDilakukan1, String date, String laboratoriumPeriksaHbAnemia1,LocalDate lmp) {
        if("ya".equalsIgnoreCase(laboratoriumPeriksaHbDilakukan1)) {
            if(fulfillMilestoneIfPossible(entityId,anmId,SCHEDULE_INA_HB_1,SCHEDULE_INA_HB_1,parse(date))){
                if ("positif".equalsIgnoreCase(laboratoriumPeriksaHbAnemia1)) {
                    scheduler.enrollIntoSchedule(entityId, SCHEDULE_INA_HB_FOLLOW, parse(date));
                } else {
                    scheduler.enrollIntoSchedule(entityId, SCHEDULE_INA_HB_2, parse(date));
                }
            }
            else if(fulfillMilestoneIfPossible(entityId,anmId,SCHEDULE_INA_HB_FOLLOW,SCHEDULE_INA_HB_FOLLOW,parse(date))){
                if (parse(date).isAfter(lmp.plusWeeks(NUMBER_OF_WEEKS_BEFORE_HB_TEST_2_BECOMES_DUE))) {
                    fulfillMilestoneIfPossible(entityId, anmId, SCHEDULE_INA_HB_2, SCHEDULE_INA_HB_2, parse(date));
                } else {
                    scheduler.enrollIntoSchedule(entityId, SCHEDULE_INA_HB_2, parse(date));
                }
            }
        }
        else {
            logger.warn("Tried to Create Schedule for HB Test but Lab test variable is selected NO  for CASE ID" + entityId);
            return;
        }
    }
}
