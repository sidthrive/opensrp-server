package org.opensrp.register.service.handler;

/**
 * Created by soran on 19/08/16.
 */
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.NutritionistService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Component;



@Component
public class NutritionistVisit implements FormSubmissionHandler {
    private NutritionistService nutritionistService;

    @Autowired
    public NutritionistVisit(NutritionistService nutritionistService) {
        this.nutritionistService = nutritionistService;
    }

    @Override
    public void handle(FormSubmission submission) {
        nutritionistService.GiziVisit(submission);
    }
}
