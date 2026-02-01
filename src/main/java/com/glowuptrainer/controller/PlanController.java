package com.glowuptrainer.controller;

import com.glowuptrainer.model.UserProfile;
import com.glowuptrainer.service.OpenAIService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlanController {

    private final OpenAIService openAIService;

    public PlanController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/plan")
    public String handlePlan(UserProfile profile, Model model) {

        System.out.println("✅ HIT /plan controller");
        System.out.println("FORM DATA -> name=" + profile.name
                + ", height=" + profile.height
                + ", weightLbs=" + profile.weightLbs
                + ", goal=" + profile.goal
                + ", workoutsPerWeek=" + profile.workoutsPerWeek);

        model.addAttribute("name", profile.name);
        model.addAttribute("height", profile.height);
        model.addAttribute("weightLbs", profile.weightLbs);
        model.addAttribute("goal", profile.goal);
        model.addAttribute("workoutsPerWeek", profile.workoutsPerWeek);

        try {
            String planText = openAIService.generatePlanText(profile);
            model.addAttribute("planText", planText);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("planText", "ERROR generating plan: " + e.getMessage());
        }

        return "result";
    }
}
