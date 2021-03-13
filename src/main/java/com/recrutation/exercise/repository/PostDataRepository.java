package com.recrutation.exercise.repository;

import com.recrutation.exercise.model.PostData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDataRepository extends JpaRepository<PostData,Integer> {
    List<PostData> findAllByUserId(Integer userID);
    List<PostData> findAllByUserIdAndTitle(Integer userID, String title);
}
