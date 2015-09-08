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
public class KbSchedulesService {
    private static Logger logger = LoggerFactory.getLogger(KbSchedulesService.class.toString());

    private HealthSchedulerService scheduler;

    @Autowired
    public KbSchedulesService(HealthSchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void kbHasHappen(String entityId, String anmId, String jenisKontrasepsiYangDigunakan, String referenceDate) {
        if ("Implant".equalsIgnoreCase(jenisKontrasepsiYangDigunakan)){
            scheduler.enrollIntoSchedule(entityId,SCHEDULE_KB_IMPLANT,parse(referenceDate));
        }
        else if("suntik_depoprovera".equalsIgnoreCase(jenisKontrasepsiYangDigunakan)){
            scheduler.enrollIntoSchedule(entityId,SCHEDULE_KB_INJECT_DEPOPROVERA, parse(referenceDate));
        }
        else if("suntik_cyclofem".equalsIgnoreCase(jenisKontrasepsiYangDigunakan)){
            scheduler.enrollIntoSchedule(entityId,SCHEDULE_KB_INJECT_CYCLOFEM, parse(referenceDate));
        }
        else if("iud".equalsIgnoreCase(jenisKontrasepsiYangDigunakan)){
            scheduler.enrollIntoSchedule(entityId,SCHEDULE_KB_IUD, parse(referenceDate));
        }

    }
}