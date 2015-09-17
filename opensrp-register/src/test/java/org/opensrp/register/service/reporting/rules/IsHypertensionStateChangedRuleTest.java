package org.opensrp.register.service.reporting.rules;

import org.opensrp.util.SafeMap;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.service.reporting.rules.IsHypertensionStateChangedRule;

import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.opensrp.common.util.EasyMap.create;

public class IsHypertensionStateChangedRuleTest {

    private IsHypertensionStateChangedRule rule;

    @Before
    public void setUp() throws Exception {
        rule = new IsHypertensionStateChangedRule();
    }

    @Test
    public void shouldReturnTrueWhenHyperTensionStateChanged() {
        Map<String, String> reportFields =
                create("id", "mother id 1")
                        .put("tandaVitalTDSistolik","140")
                        .put("tandaVitalTDDiastolik", "90")
                        .put("previousBpSystolic", "130")
                        .put("previousBpDiastolic", "80")
                        .map();

        assertTrue(rule.apply(new SafeMap(reportFields)));

        reportFields =
                create("id", "mother id 1")
                        .put("tandaVitalTDSistolik","140")
                        .put("tandaVitalTDDiastolik", "80")
                        .put("previousBpSystolic", "130")
                        .put("previousBpDiastolic", "80")
                        .map();

        assertTrue(rule.apply(new SafeMap(reportFields)));


        reportFields =
                create("id", "mother id 1")
                        .put("tandaVitalTDSistolik","120")
                        .put("tandaVitalTDDiastolik", "90")
                        .put("previousBpSystolic", "130")
                        .put("previousBpDiastolic", "80")
                        .map();

        assertTrue(rule.apply(new SafeMap(reportFields)));
    }

    @Test
    public void shouldReturnFalseWhenHyperTensionStateIsNotChanged() {
        Map<String, String> reportFields =
                create("id", "mother id 1")
                        .put("tandaVitalTDSistolik", "125")
                        .put("tandaVitalTDDiastolik", "80")
                        .put("previousBpSystolic", "130")
                        .put("previousBpDiastolic", "80")

                        .map();

        assertFalse(rule.apply(new SafeMap(reportFields)));
    }
}
