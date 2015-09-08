package org.opensrp.register.service.scheduling;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.joda.time.LocalDate.parse;
import static org.opensrp.common.util.IntegerUtil.tryParse;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_1;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_2;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_3;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_4;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_MILESTONE_PREFIX;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_DELIVERY_PLAN;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_EDD;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_HB_FOLLOWUP_TEST;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_HB_TEST_1;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_HB_TEST_2;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_IFA_1;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_IFA_2;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_IFA_3;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_LAB;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_TT_1;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_TT_2;

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
public class KbSchedulesService {
    private static Logger logger = LoggerFactory.getLogger(KbSchedulesService.class.toString());

    private HealthSchedulerService scheduler;

    @Autowired
    public KbSchedulesService(HealthSchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void kbHasHappen(String entityId, String anmId, String jenisKontrasepsiYangDigunakan, String referenceDate) {
        if ("Implant".equalsIgnoreCase(jenisKontrasepsiYangDigunakan)){
            scheduler.enrollIntoSchedule(entityId,"KB Implant",parse(referenceDate));
        }
        else if("suntik_depoprovera".equalsIgnoreCase(jenisKontrasepsiYangDigunakan)){
            scheduler.enrollIntoSchedule(entityId,"KB Injection Depoprovera", parse(referenceDate));
        }

    }
}