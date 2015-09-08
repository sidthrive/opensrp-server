package org.opensrp.register;

import org.omg.CORBA.PUBLIC_MEMBER;

public class DrishtiScheduleConstants {
	public static class OpenSRPEvent{
		public static final String FORM_SUBMISSION = "FORM_SUBMISSION";
	}
    public static final String PROVIDER_REPORT_SCHEDULE_SUBJECT = "PROVIDER-REPORT-SCHEDULE";
    public static final String FORM_SCHEDULE_SUBJECT = "FORM-SCHEDULE";
    public static final String MCTS_REPORT_SCHEDULE_SUBJECT = "MCTS-REPORT-SCHEDULE";
    public static final String ANM_REPORT_SCHEDULE_SUBJECT = "DRISHTI-ANM-REPORT-FETCH-SCHEDULE";

    public static class ChildScheduleConstants {
        public static final String CHILD_SCHEDULE_BCG = "BCG";

        public static final String CHILD_SCHEDULE_DPT_BOOSTER1 = "DPT Booster 1";
        public static final String CHILD_SCHEDULE_DPT_BOOSTER2 = "DPT Booster 2";

        public static final String CHILD_SCHEDULE_MEASLES = "Measles Vaccination";

        public static final String CHILD_SCHEDULE_MEASLES_BOOSTER = "Measles Booster";

        public static final String CHILD_SCHEDULE_OPV_0_AND_1 = "OPV_0_AND_1";
        public static final String CHILD_SCHEDULE_OPV_2 = "OPV 2";
        public static final String CHILD_SCHEDULE_OPV_3 = "OPV 3";
        public static final String CHILD_SCHEDULE_OPV_BOOSTER = "OPV BOOSTER";

        public static final String CHILD_SCHEDULE_PENTAVALENT_1 = "PENTAVALENT 1";
        public static final String CHILD_SCHEDULE_PENTAVALENT_2 = "PENTAVALENT 2";
        public static final String CHILD_SCHEDULE_PENTAVALENT_3 = "PENTAVALENT 3";
    }

    public static class MotherScheduleConstants {
        public static final String SCHEDULE_ANC = "Ante Natal Care - Normal";
        public static final String SCHEDULE_ANC_MILESTONE_PREFIX = "ANC";
        public static final String SCHEDULE_ANC_1 = "ANC 1";
        public static final String SCHEDULE_ANC_2 = "ANC 2";
        public static final String SCHEDULE_ANC_3 = "ANC 3";
        public static final String SCHEDULE_ANC_4 = "ANC 4";
        public static final String SCHEDULE_EDD = "Expected Date Of Delivery";
        public static final String SCHEDULE_LAB = "Lab Reminders";
        public static final String SCHEDULE_TT_1 = "TT 1";
        public static final String SCHEDULE_TT_2 = "TT 2";
        public static final String SCHEDULE_AUTO_CLOSE_PNC = "Auto Close PNC";
        public static final String SCHEDULE_IFA_1 = "IFA 1";
        public static final String SCHEDULE_IFA_2 = "IFA 2";
        public static final String SCHEDULE_IFA_3 = "IFA 3";
        public static final String SCHEDULE_HB_TEST_1 = "Hb Test 1";
        public static final String SCHEDULE_HB_TEST_2 = "Hb Test 2";
        public static final String SCHEDULE_HB_FOLLOWUP_TEST = "Hb Followup Test";
        public static final String SCHEDULE_DELIVERY_PLAN = "Delivery Plan";
        public static final String SCHEDULE_TT_INA_1 = "TT KE 1";
        public static final String SCHEDULE_TT_INA_2 = "TT KE 2";
        public static final String SCHEDULE_KB_IMPLANT = "KB Implant";
        public static final String SCHEDULE_KB_INJECT_DEPOPROVERA = "KB Injection Depoprovera";
        public static final String SCHEDULE_KB_INJECT_CYCLOFEM = "KB Injection Cyclofem";
        public static final String SCHEDULE_KB_IUD = "KB IUD";

    }

    public static class ECSchedulesConstants {
        public static final String EC_SCHEDULE_DMPA_INJECTABLE_REFILL = "DMPA Injectable Refill";
        public static final String EC_SCHEDULE_DMPA_INJECTABLE_REFILL_MILESTONE = "DMPA Injectable Refill";
        public static final String EC_SCHEDULE_OCP_REFILL = "OCP Refill";
        public static final String EC_SCHEDULE_OCP_REFILL_MILESTONE = "OCP Refill";
        public static final String EC_SCHEDULE_CONDOM_REFILL = "Condom Refill";
        public static final String EC_SCHEDULE_CONDOM_REFILL_MILESTONE = "Condom Refill";
        public static final String EC_SCHEDULE_FEMALE_STERILIZATION_FOLLOWUP = "Female sterilization Followup";
        public static final String EC_SCHEDULE_FEMALE_STERILIZATION_FOLLOWUP_MILESTONE_1 = "Female sterilization Followup 1";
        public static final String EC_SCHEDULE_FEMALE_STERILIZATION_FOLLOWUP_MILESTONE_2 = "Female sterilization Followup 2";
        public static final String EC_SCHEDULE_FEMALE_STERILIZATION_FOLLOWUP_MILESTONE_3 = "Female sterilization Followup 3";
        public static final String EC_SCHEDULE_MALE_STERILIZATION_FOLLOWUP = "Male sterilization Followup";
        public static final String EC_SCHEDULE_MALE_STERILIZATION_FOLLOWUP_MILESTONE_1 = "Male sterilization Followup 1";
        public static final String EC_SCHEDULE_MALE_STERILIZATION_FOLLOWUP_MILESTONE_2 = "Male sterilization Followup 2";
        public static final String EC_SCHEDULE_FP_FOLLOWUP = "FP Followup";
        public static final String EC_SCHEDULE_FP_FOLLOWUP_MILESTONE = "FP Followup";
        public static final String EC_SCHEDULE_IUD_FOLLOWUP = "IUD Followup";
        public static final String EC_SCHEDULE_IUD_FOLLOWUP_MILESTONE_1 = "IUD Followup 1";
        public static final String EC_SCHEDULE_IUD_FOLLOWUP_MILESTONE_2 = "IUD Followup 2";
        public static final String EC_SCHEDULE_FP_REFERRAL_FOLLOWUP = "FP Referral Followup";
        public static final String EC_SCHEDULE_FP_REFERRAL_FOLLOWUP_MILESTONE = "FP Referral Followup";
    }
}
