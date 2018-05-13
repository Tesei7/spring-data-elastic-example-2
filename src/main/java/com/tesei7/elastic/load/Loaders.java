package com.tesei7.elastic.load;

import com.tesei7.elastic.model.User;
import com.tesei7.elastic.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class Loaders {
    @Autowired
    private ElasticsearchOperations operations;
    @Autowired
    private UsersRepository usersRepository;

    @PostConstruct
    @Transactional
    public void loadAll() {
        operations.putMapping(User.class);
        log.info("Loading Data");
        usersRepository.save(getData());
        log.info("Loading Completed");
    }

    private List<User> getData() {
        return Stream.of(
                new User(123L, "Ajay", "Accounting", 12000L),
                new User(1230L, "Techie", "Accounting", 12000L),
                new User(1234L, "Jaga", "Finance", 22000L),
                new User(124L, "Shiva", "Tech", 21000L),
                new User(14L, "Karthick", "Tech", 21000L),
                new User(4L, "Bhuvanesh", "Accounting", 21000L),
                new User(1L, "Kumaran", "Tech", 22000L),
                new User(1235L, "Thiru", "Accounting", 12000L))
                .collect(Collectors.toList());
    }
}
