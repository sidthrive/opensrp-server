package org.ei.drishti.controller;

import org.ei.commcare.listener.CommCareFormSubmissionRouter;
import org.ei.drishti.contract.BirthPlanningRequest;
import org.ei.drishti.contract.ChildCloseRequest;
import org.ei.drishti.service.ANCService;
import org.ei.drishti.service.ChildService;
import org.ei.drishti.service.DrishtiMCTSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DrishtiController {
    private static Logger logger = LoggerFactory.getLogger(DrishtiController.class.toString());
    private final ANCService ancService;
    private final ChildService childService;
    private DrishtiMCTSService mctsService;

    @Autowired
    public DrishtiController(CommCareFormSubmissionRouter router, ANCService ancService,
                             ChildService childService, DrishtiMCTSService drishtiMctsService) {
        router.registerForDispatch(this);
        this.ancService = ancService;
        this.childService = childService;
        this.mctsService = drishtiMctsService;
    }

    public void closeChildCase(ChildCloseRequest childCloseRequest, Map<String, Map<String, String>> extraData) {
        logger.info("Child close: " + childCloseRequest);

        childService.closeChildCase(childCloseRequest, extraData);
        mctsService.closeChildCase(childCloseRequest);
    }

    public void updateBirthPlanning(BirthPlanningRequest request, Map<String, Map<String, String>> extraData) {
        logger.info("Birth planning: " + request + ". Extra data: " + extraData);

        ancService.updateBirthPlanning(request, extraData);
    }
}
