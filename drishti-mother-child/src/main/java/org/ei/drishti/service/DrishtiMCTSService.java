package org.ei.drishti.service;

import org.ei.drishti.contract.*;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllMothers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.ei.drishti.service.MCTSSMSService.MCTSServiceCode.PNC_7_Days;

@Service
public class DrishtiMCTSService {
    private static Logger logger = LoggerFactory.getLogger(DrishtiMCTSService.class.toString());

    private final MCTSSMSService mctsSMSService;
    private final AllMothers mothers;

    @Autowired
    public DrishtiMCTSService(MCTSSMSService mctsSMSService, AllMothers mothers) {
        this.mctsSMSService = mctsSMSService;
        this.mothers = mothers;
    }

    public void closeChildCase(ChildCloseRequest childCloseRequest) {
    }
}
