package org.opensrp.register.action;

import static org.motechproject.scheduletracking.api.domain.WindowName.due;
import static org.motechproject.scheduletracking.api.domain.WindowName.earliest;
import static org.motechproject.scheduletracking.api.domain.WindowName.late;
import static org.motechproject.scheduletracking.api.domain.WindowName.max;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_BCG;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_DPT_BOOSTER1;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_DPT_BOOSTER2;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_MEASLES;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_MEASLES_BOOSTER;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_OPV_0_AND_1;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_OPV_2;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_OPV_3;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_OPV_BOOSTER;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_PENTAVALENT_1;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_PENTAVALENT_2;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.CHILD_SCHEDULE_PENTAVALENT_3;
import static org.opensrp.register.DrishtiScheduleConstants.ECSchedulesConstants.EC_SCHEDULE_CONDOM_REFILL;
import static org.opensrp.register.DrishtiScheduleConstants.ECSchedulesConstants.EC_SCHEDULE_DMPA_INJECTABLE_REFILL;
import static org.opensrp.register.DrishtiScheduleConstants.ECSchedulesConstants.EC_SCHEDULE_FEMALE_STERILIZATION_FOLLOWUP;
import static org.opensrp.register.DrishtiScheduleConstants.ECSchedulesConstants.EC_SCHEDULE_FP_FOLLOWUP;
import static org.opensrp.register.DrishtiScheduleConstants.ECSchedulesConstants.EC_SCHEDULE_FP_REFERRAL_FOLLOWUP_MILESTONE;
import static org.opensrp.register.DrishtiScheduleConstants.ECSchedulesConstants.EC_SCHEDULE_IUD_FOLLOWUP;
import static org.opensrp.register.DrishtiScheduleConstants.ECSchedulesConstants.EC_SCHEDULE_MALE_STERILIZATION_FOLLOWUP;
import static org.opensrp.register.DrishtiScheduleConstants.ECSchedulesConstants.EC_SCHEDULE_OCP_REFILL;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.*;
import static org.opensrp.scheduler.Matcher.any;
import static org.opensrp.scheduler.Matcher.anyOf;
import static org.opensrp.scheduler.Matcher.eq;

import org.opensrp.scheduler.HookedEvent;
import org.opensrp.scheduler.Matcher;
import org.opensrp.scheduler.TaskSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AlertHandler {
    @Autowired
    public AlertHandler(TaskSchedulerService scheduler,
                           @Qualifier("ForceFulfillAction") HookedEvent forceFulfill,
                           @Qualifier("AlertCreationAction") HookedEvent alertCreation,
                           @Qualifier("AutoClosePNCAction") HookedEvent autoClosePNCAction) {
    	scheduler.addHookedEvent(eq(SCHEDULE_ANC), any(), eq(max.toString()), forceFulfill);
    	scheduler.addHookedEvent(eq(SCHEDULE_LAB), any(), eq(max.toString()), forceFulfill);
    	scheduler.addHookedEvent(eq(SCHEDULE_AUTO_CLOSE_PNC), any(), any(), autoClosePNCAction);
    	scheduler.addHookedEvent(motherSchedules(), any(), anyOf(earliest.toString(), due.toString(), late.toString()),
                alertCreation).addExtraData("beneficiaryType", "mother");
    	scheduler.addHookedEvent(childSchedules(), any(), anyOf(earliest.toString(), due.toString(),
                late.toString(), max.toString()), 
                alertCreation).addExtraData("beneficiaryType", "child");
    	scheduler.addHookedEvent(ecSchedules(), any(), anyOf(earliest.toString(), due.toString(), late.toString()),
                alertCreation).addExtraData("beneficiaryType", "ec");
    }

    private Matcher childSchedules() {
        return anyOf(CHILD_SCHEDULE_BCG,

                CHILD_SCHEDULE_DPT_BOOSTER1,
                CHILD_SCHEDULE_DPT_BOOSTER2,

                CHILD_SCHEDULE_MEASLES,
                CHILD_SCHEDULE_MEASLES_BOOSTER,

                CHILD_SCHEDULE_OPV_0_AND_1,
                CHILD_SCHEDULE_OPV_2,
                CHILD_SCHEDULE_OPV_3,
                CHILD_SCHEDULE_OPV_BOOSTER,

                CHILD_SCHEDULE_PENTAVALENT_1,
                CHILD_SCHEDULE_PENTAVALENT_2,
                CHILD_SCHEDULE_PENTAVALENT_3
        );
    }

    private Matcher motherSchedules() {
        return anyOf(SCHEDULE_ANC, SCHEDULE_TT_1, SCHEDULE_TT_2, SCHEDULE_IFA_1, SCHEDULE_IFA_2, SCHEDULE_IFA_3,
                SCHEDULE_LAB, SCHEDULE_EDD, SCHEDULE_HB_TEST_1, SCHEDULE_HB_TEST_2, SCHEDULE_HB_FOLLOWUP_TEST,
                SCHEDULE_DELIVERY_PLAN, SCHEDULE_TT_INA_1, SCHEDULE_TT_INA_2, SCHEDULE_KB_IMPLANT,SCHEDULE_KB_INJECT_DEPOPROVERA,
                SCHEDULE_KB_INJECT_CYCLOFEM,SCHEDULE_KB_IUD,SCHEDULE_INA_HB_1,SCHEDULE_INA_HB_FOLLOW,SCHEDULE_INA_HB_2,
                SCHEDULE_INA_IFA_1, SCHEDULE_INA_IFA_2, SCHEDULE_INA_IFA_3, SCHEDULE_INA_PNC_1, "PNC 2", "PNC 3");
    }

    private Matcher ecSchedules() {
        return anyOf(EC_SCHEDULE_DMPA_INJECTABLE_REFILL,
                EC_SCHEDULE_OCP_REFILL,
                EC_SCHEDULE_CONDOM_REFILL,
                EC_SCHEDULE_FEMALE_STERILIZATION_FOLLOWUP,
                EC_SCHEDULE_MALE_STERILIZATION_FOLLOWUP,
                EC_SCHEDULE_IUD_FOLLOWUP,
                EC_SCHEDULE_FP_FOLLOWUP,
                EC_SCHEDULE_FP_REFERRAL_FOLLOWUP_MILESTONE);
    }
}
