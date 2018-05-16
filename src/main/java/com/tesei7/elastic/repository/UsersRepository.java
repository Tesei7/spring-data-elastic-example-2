package com.tesei7.elastic.repository;

import com.tesei7.elastic.model.User;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UsersRepository extends UsersRepositoryCustomized, ElasticsearchRepository<User, Long> {
    List<User> findByName(String text);

    List<User> findBySalary(Long salary);

    @Query("{\"range\" : {" +
            "   \"salary\" : {" +
            "       \"gte\" : \"?0\"" +
            "   }" +
            "}}")
    List<User> findSalaryGreater(Long value);

    @Query("{\"range\" : {" +
            "   \"salary\" : {" +
            "       \"gte\" : \"?0\"," +
            "       \"lte\" : \"?1\"" +
            "   }" +
            "}}")
    List<User> findSalaryRange(Long from, Long to);

    @Query("{\"term\" : {" +
            "   \"teamName\" : \"?0\"" +
            "}}")
    List<User> findByTeam(String teamName);

    @Query(" {\"nested\" : {" +
            "    \"path\" : \"projects\"," +
            "    \"query\" : {" +
            "       \"match\" : { \"projects.name\" : \"?0\" }" +
            "    }" +
            "}}")
    List<User> findByProjects(String project);

    @Query("{" +
            "    \"query\": {" +
            "        \"bool\": {" +
            "            \"must\": [{" +
            "                    \"range\": {" +
            "                        \"salary\": {" +
            "                            \"lte\": \"?0\"" +
            "                        }" +
            "                    }" +
            "                }, {" +
            "                    \"nested\": {" +
            "                        \"path\": \"projects\"," +
            "                        \"query\": {" +
            "                            \"range\": {" +
            "                                \"projects.endDate\": {" +
            "                                    \"lt\": \"now/d\"" +
            "                                }" +
            "                            }" +
            "                        }" +
            "                    }" +
            "                }" +
            "            ]" +
            "        }" +
            "    }" +
            "}")
    List<User> findWithNonactiveProjectAndSalaryLess(Long salary);
}
