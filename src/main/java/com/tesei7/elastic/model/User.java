package com.tesei7.elastic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "users", type = "users")
public class User {
    private Long id;
    private String name;
    private String teamName;
    private Long salary;
}
