package org.opensrp.register.service.reporting.rules;

import org.opensrp.common.util.EasyMap;
import org.opensrp.util.SafeMap;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.service.reporting.rules.IsFirstANCVisitRule;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class IsFirstANCVisitRuleTest {

    private IsFirstANCVisitRule rule;

    @Before
    public void setUp() throws Exception {
        rule = new IsFirstANCVisitRule();
    }

    @Test
    public void shouldPassWhenANCVisitIsTheFirstOne() throws Exception {
        Map<String, String> fields = EasyMap.create("ancKe", "1").map();

        assertTrue(rule.apply(new SafeMap(fields)));
    }

    @Test
    public void shouldFailWhenANCVisitIsNotTheFirstOne() throws Exception {
        Map<String, String> fields = EasyMap.create("ancKe", "2").map();

        assertFalse(rule.apply(new SafeMap(fields)));
    }
}
