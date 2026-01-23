package com.glowuptrainer.controller;

import com.glowuptrainer.model.PlanResponse;
import com.glowuptrainer.model.UserProfile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    @PostMapping("/generate")
    public PlanResponse generatePlan(@RequestBody UserProfile profile) {
        return new PlanResponse(
                "Plan generated",
                profile.name,
                profile.goal
        );
    }
}
