package com.glowuptrainer.service;

import com.glowuptrainer.model.UserProfile;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    private final OpenAIClient client;

    public OpenAIService(@Value("${openai.api.key}") String apiKey) {
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    public String generatePlanText(UserProfile profile) {

        String prompt =
                "Height: " + profile.height + "\n" +
                        "WeightLbs: " + profile.weightLbs + "\n" +
                        "Goal: " + profile.goal + "\n" +
                        "WorkoutsPerWeek: " + profile.workoutsPerWeek + "\n\n" +

                        "Format your answer EXACTLY like this:\n" +
                        "TITLE: 7-Day Plan\n" +
                        "CALORIES: <number>\n" +
                        "MACROS: Protein <g>, Carbs <g>, Fat <g>\n\n" +
                        "WORKOUT PLAN:\n" +
                        "Day 1: ...\n" +
                        "Day 2: ...\n" +
                        "Day 3: ...\n" +
                        "Day 4: ...\n" +
                        "Day 5: ...\n" +
                        "Day 6: ...\n" +
                        "Day 7: ...\n\n" +
                        "MEAL PLAN:\n" +
                        "Day 1:\n" +
                        "- Breakfast: ...\n" +
                        "- Lunch: ...\n" +
                        "- Dinner: ...\n" +
                        "- Snack: ...\n\n" +
                        "GROCERY LIST:\n" +
                        "- item\n" +
                        "- item\n\n" +
                        "End with: General info only, not medical advice.";

        Response response = client.responses().create(
                ResponseCreateParams.builder()
                        .model("gpt-4.1-mini")
                        .input(prompt)
                        .build()
        );

        String raw = response.toString();

// Grab only the actual generated text from the response string
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(
                "text=(.*?)(?:,\\s*type=output_text|\\s*\\}\\]\\s*,\\s*role=assistant)",
                java.util.regex.Pattern.DOTALL
        );
        java.util.regex.Matcher m = p.matcher(raw);

        if (m.find()) {
            return m.group(1).trim();
        }

        return raw; // fallback

    }
}
