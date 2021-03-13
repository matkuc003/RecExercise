package com.recrutation.exercise.controller;

import com.recrutation.exercise.model.PostData;
import com.recrutation.exercise.service.PostDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostDataController {
    @Autowired
    PostDataService postDataService;

    @PostMapping("/download")
    public ResponseEntity<Boolean> downloadData(){
        return postDataService.downloadData();
    }
    @GetMapping("/displayPosts/{userID}")
    public ResponseEntity<List<PostData>> displayPostsByUserID(@PathVariable Integer userID){
        return postDataService.displayPostsUserId(userID);
    }
    @GetMapping("/displayPosts/{userID}/{title}")
    public ResponseEntity<List<PostData>> displayPostsByTitle(@PathVariable Integer userID, @PathVariable String title){
        return postDataService.displayPostByTitle(userID,title);
    }
    @DeleteMapping("/delete/{postID}")
    public ResponseEntity<Boolean> deletePostByPostID(@PathVariable Integer postID){
        return postDataService.deletePost(postID);
    }
    @PutMapping("/edit/{postID}")
    public ResponseEntity<Boolean> editPost(@RequestBody PostData post, @PathVariable Integer postID){
        return postDataService.editPost(postID, post.getBody(), post.getTitle());
    }
}
