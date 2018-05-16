package com.tesei7.elastic.repository;

import com.tesei7.elastic.model.User;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

@Repository
@Transactional(readOnly = true)
public class UsersRepositoryImpl implements UsersRepositoryCustomized {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<User> getAll(String text) {
        QueryBuilder query = boolQuery()
                .should(queryStringQuery(text)
                        .field("name")
                        .field("teamName"))
                .should(queryStringQuery("*" + text + "*")
                        .field("name")
                        .field("teamName"));
        NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(query).build();
        return elasticsearchTemplate.queryForList(build, User.class);
    }

    @Override
    public List<Map<String, Object>> getSalaryRangeHidden(Long from, Long to) {
        SourceFilter sourceFilter = new FetchSourceFilter(null, new String[]{"projects.startDate", "salary"});
        QueryBuilder query = rangeQuery("salary").from(from).to(to);
        NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(query).withSourceFilter(sourceFilter).build();
        return elasticsearchTemplate.query(build,
                response -> Stream.of(response.getHits().getHits()).map(SearchHit::getSource).collect(toList()));
    }
}
