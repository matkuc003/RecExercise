package com.recrutation.exercise.scheduler;

import com.recrutation.exercise.model.PostData;
import com.recrutation.exercise.repository.PostDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;


@Component
@EnableScheduling
public class ScheduledMethods {
    @Value("${jobs.enabled:true}")
    private boolean isEnabled;
    @Autowired
    PostDataRepository postDataRepository;
    private final RestTemplate restTemplate;

    ScheduledMethods(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    @Scheduled(cron = "0 0 10 * * *")
    public void invokeDownload() {
        if (isEnabled) {
            String url = "https://jsonplaceholder.typicode.com/posts";
            ResponseEntity<PostData[]> response = this.restTemplate.getForEntity(url, PostData[].class);
            List<PostData> postDataListFromResponse = Arrays.asList(response.getBody());
            List<PostData> postDataListInsideDB = postDataRepository.findAll();
            if(postDataListInsideDB.size()>0) {
                postDataListFromResponse.stream()
                        .filter(item -> postDataListInsideDB.contains(item))
                        .forEach(postData -> postDataRepository.save(postData));
            }
            else{
                postDataRepository.saveAll(postDataListFromResponse);
            }
            System.out.println("Daily update: "+response.getBody());
        }
    }
}
