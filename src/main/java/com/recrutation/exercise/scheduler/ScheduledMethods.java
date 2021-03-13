package com.recrutation.exercise.scheduler;

import com.recrutation.exercise.service.PostDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledMethods {
    @Value("${jobs.enabled:true}")
    private boolean isEnabled;
    @Autowired
    private PostDataService postDataService;

    @Scheduled(cron = "0 3 15 * * *")
    public void invokeDownload() {
        if (isEnabled) {
            ResponseEntity response = postDataService.downloadData();
            System.out.println("Daily update: "+response.getBody());
        }
    }
}
