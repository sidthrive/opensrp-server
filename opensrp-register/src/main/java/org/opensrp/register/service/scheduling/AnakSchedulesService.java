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

        scheduler.enrollIntoSchedule(childId, "BCG",parse(referenceDate));
        scheduler.enrollIntoSchedule(childId, "Measles Vaccination", parse(referenceDate));
        //  scheduler.enrollIntoSchedule(childId, "Measles Vaccination",parse(referenceDate));


    }
}
