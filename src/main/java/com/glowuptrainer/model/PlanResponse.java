package com.glowuptrainer.model;

public class PlanResponse {
    public String message;
    public String name;
    public String goal;

    public PlanResponse(String message, String name, String goal) {
        this.message = message;
        this.name = name;
        this.goal = goal;
    }
}
