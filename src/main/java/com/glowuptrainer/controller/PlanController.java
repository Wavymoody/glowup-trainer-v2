package com.glowuptrainer.controller;

import com.glowuptrainer.model.UserProfile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    @PostMapping("/generate")
    public String generatePlan(@RequestBody UserProfile profile) {
        return "Plan generated for " + profile.name +
                " with goal: " + profile.goal;
    }
}
