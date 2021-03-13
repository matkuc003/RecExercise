package com.recrutation.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostData {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer userId;
    @Id
    private Integer id;
    private String title;
    private String body;
}
