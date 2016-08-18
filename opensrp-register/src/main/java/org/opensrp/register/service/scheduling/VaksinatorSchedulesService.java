package org.opensrp.register.service.scheduling;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.joda.time.LocalDate.parse;
import static org.opensrp.register.DrishtiScheduleConstants.ChildScheduleConstants.*;

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
import org.opensrp.common.AllConstants.KbFormFields.*;

@Service
public class VaksinatorSchedulesService {
    private static Logger logger = LoggerFactory.getLogger(KbSchedulesService.class.toString());

    private HealthSchedulerService scheduler;

    @Autowired
    public VaksinatorSchedulesService(HealthSchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    //enroll schedule for kb_registration
    public void registered(String entityId, String anmId, String referenceDate) {
        scheduler.enrollIntoSchedule(entityId,CHILD_SCHEDULE_HB0_VAKSINATOR,parse(referenceDate));
        scheduler.enrollIntoSchedule(entityId,CHILD_SCHEDULE_BCG_POLIO_1_VAKSINATOR,parse(referenceDate));
        scheduler.enrollIntoSchedule(entityId,CHILD_SCHEDULE_HB1_VAKSINATOR,parse(referenceDate));
        scheduler.enrollIntoSchedule(entityId,CHILD_SCHEDULE_HB2_VAKSINATOR,parse(referenceDate));
        scheduler.enrollIntoSchedule(entityId,CHILD_SCHEDULE_HB3_VAKSINATOR,parse(referenceDate));
        scheduler.enrollIntoSchedule(entityId,CHILD_SCHEDULE_CAMPAK_VAKSINATOR,parse(referenceDate));
        scheduler.enrollIntoSchedule(entityId,CHILD_SCHEDULE_CAMPAK_LANJUTAN_VAKSINATOR,parse(referenceDate));
    }

    public void hasGiven(String form, String entityId, String anmId) {
        if(form.contains(AllConstants.Form.HB0_VISIT))
            scheduler.unEnrollFromSchedule(entityId,anmId,CHILD_SCHEDULE_HB0_VAKSINATOR);
        else if(form.contains(AllConstants.Form.BCG_VISIT))
            scheduler.unEnrollFromSchedule(entityId,anmId,CHILD_SCHEDULE_BCG_POLIO_1_VAKSINATOR);
        else if(form.contains(AllConstants.Form.HB1_VISIT))
            scheduler.unEnrollFromSchedule(entityId,anmId,CHILD_SCHEDULE_HB1_VAKSINATOR);
        else if(form.contains(AllConstants.Form.HB2_VISIT))
            scheduler.unEnrollFromSchedule(entityId,anmId,CHILD_SCHEDULE_HB2_VAKSINATOR);
        else if(form.contains(AllConstants.Form.HB3_VISIT))
            scheduler.unEnrollFromSchedule(entityId,anmId,CHILD_SCHEDULE_HB3_VAKSINATOR);
        else if(form.equalsIgnoreCase(AllConstants.Form.CAMPAK_VISIT))
            scheduler.unEnrollFromSchedule(entityId,anmId,CHILD_SCHEDULE_CAMPAK);
        else if(form.contains(AllConstants.Form.CAMPAK_LANJUTAN_VISIT))
            scheduler.unEnrollFromSchedule(entityId,anmId,CHILD_SCHEDULE_CAMPAK_LANJUTAN_VAKSINATOR);

    }
}