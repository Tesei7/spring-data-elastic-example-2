package com.tesei7.elastic.model;

import lombok.*;
import lombok.experimental.Tolerate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Builder
@Document(indexName = "users", type = "users", shards = 1, replicas = 0)
public class User {
    private Long id;
    private String name;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String teamName;
    private Long salary;
    @Field(type = FieldType.Nested)
    private List<Project> projects;

    @Tolerate
    public User() {}
}
