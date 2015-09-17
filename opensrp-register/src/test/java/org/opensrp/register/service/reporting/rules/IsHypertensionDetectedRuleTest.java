package org.opensrp.register.service.reporting.rules;

import org.opensrp.util.SafeMap;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.service.reporting.rules.IsHypertensionDetectedRule;

import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.opensrp.common.util.EasyMap.create;

public class IsHypertensionDetectedRuleTest {

    private IsHypertensionDetectedRule isHypertensionDetectedRule;

    @Before
    public void setUp() throws Exception {
        isHypertensionDetectedRule = new IsHypertensionDetectedRule();
    }

    @Test
    public void shouldReturnTrueWhenHyperTensionDetected() {
        Map<String, String> reportFields =
                create("id", "mother id 1")
                        .put("tandaVitalTDSistolik","140")
                        .put("tandaVitalTDDiastolik", "90")
                        .map();

        assertTrue(isHypertensionDetectedRule.apply(new SafeMap(reportFields)));

        reportFields =
                create("id", "mother id 1")
                        .put("tandaVitalTDSistolik","145")
                        .put("tandaVitalTDDiastolik", "70")
                        .map();

        assertTrue(isHypertensionDetectedRule.apply(new SafeMap(reportFields)));


        reportFields =
                create("id", "mother id 1")
                        .put("tandaVitalTDSistolik","120")
                        .put("tandaVitalTDDiastolik", "90")
                        .map();

        assertTrue(isHypertensionDetectedRule.apply(new SafeMap(reportFields)));
    }

    @Test
    public void shouldReturnFalseWhenHyperTensionDetected() {
        Map<String, String> reportFields =
                create("id", "mother id 1")
                        .put("tandaVitalTDSistolik", "125")
                        .put("tandaVitalTDDiastolik", "80")
                        .map();

        assertFalse(isHypertensionDetectedRule.apply(new SafeMap(reportFields)));
    }
}
