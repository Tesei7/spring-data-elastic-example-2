package com.tesei7.elastic.repository;

import com.tesei7.elastic.model.User;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UsersRepository extends UsersRepositoryCustomized, ElasticsearchRepository<User, Long> {
    List<User> findByName(String text);

    List<User> findBySalary(Long salary);

    @Query("{\"bool\" : {\"must\" : {\"range\" : {\"salary\" : {\"from\" : \"?0\", \"to\" : null}}}}}")
    List<User> findSalaryGreater(Long value);
}
