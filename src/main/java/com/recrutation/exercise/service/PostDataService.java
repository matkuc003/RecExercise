package com.recrutation.exercise.service;
import com.recrutation.exercise.model.PostData;
import com.recrutation.exercise.repository.PostDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class PostDataService {
    @Autowired
    PostDataRepository postDataRepository;

    private final RestTemplate restTemplate;

    PostDataService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<Boolean> downloadData() {
        try {
            String url = "https://jsonplaceholder.typicode.com/posts";
            ResponseEntity<PostData[]> response = this.restTemplate.getForEntity(url, PostData[].class);
            List<PostData> postDataList = Arrays.asList(response.getBody());
            postDataRepository.saveAll(postDataList);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception ex) {
            {
                ex.printStackTrace();
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }
    }

    public ResponseEntity<List<PostData>> displayPostsByUserId(Integer userID) {
        try {
            List<PostData> postDataList = postDataRepository.findAllByUserId(userID);
            return new ResponseEntity<>(postDataList, HttpStatus.OK);
        } catch (Exception ex) {
            {
                ex.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }

    public ResponseEntity<List<PostData>> displayPostByTitle(String title) {
        try {
            List<PostData> postDataList = postDataRepository.findAllByTitle(title);
            return new ResponseEntity<>(postDataList, HttpStatus.OK);
        } catch (Exception ex) {
            {
                ex.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }

    public ResponseEntity<Boolean> deletePost(Integer ID) {
        try {
            postDataRepository.deleteById(ID);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception ex) {
            {
                ex.printStackTrace();
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }
    }

    public ResponseEntity<Boolean> editPost(Integer postID, String body, String title) {
        try {
            PostData postDataToEdit = postDataRepository.findById(postID).get();
            postDataToEdit.setBody(body);
            postDataToEdit.setTitle(title);
            postDataRepository.save(postDataToEdit);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception ex) {
            {
                ex.printStackTrace();
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
