package com.tesei7.elastic.repository;

import com.tesei7.elastic.model.User;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UsersRepositoryImpl implements UsersRepositoryCustomized {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<User> getAll(String text) {
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.queryStringQuery(text).lenient(true)
                        .field("name")
                        .field("teamName")
                ).should(QueryBuilders.queryStringQuery("*" + text + "*").lenient(true)
                        .field("name")
                        .field("teamName"));
        NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(query).build();
        return elasticsearchTemplate.queryForList(build, User.class);
    }
}
