package com.tesei7.elastic.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Builder
public class Project {
    private String name;
    @Field(type = FieldType.Date)
    private Date startDate;
    @Field(type = FieldType.Date)
    private Date endDate;
    private String description;

    @Tolerate
    public Project(){}
}
