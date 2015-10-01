package org.opensrp.register.service.scheduling;

import org.opensrp.scheduler.HealthSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;
import static org.joda.time.LocalDate.parse;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.*;
import static org.opensrp.register.DrishtiScheduleConstants.MotherScheduleConstants.SCHEDULE_INA_PNC_1;

/**
 * Created by Iq on 02/10/15.
 */
@Service
public class PncInaScheduleService {
    private static Logger logger = LoggerFactory.getLogger(PncInaScheduleService.class.toString());

    private HealthSchedulerService scheduler;

    @Autowired
    public PncInaScheduleService(HealthSchedulerService scheduler) {
        this.scheduler = scheduler;
    }
    public void PersalinanisDone(String entityId, String date) {
        logger.info(format("Enrolling mother into PNC 1 schedule. Id: ", entityId));

        scheduler.enrollIntoSchedule(entityId, SCHEDULE_INA_PNC_1, date);
    }
    public void unEnrollFromSchedules(String entityId) {
        scheduler.unEnrollFromAllSchedules(entityId);
    }
}
