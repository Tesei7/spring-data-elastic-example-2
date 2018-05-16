package com.tesei7.elastic.load;

import com.tesei7.elastic.model.Project;
import com.tesei7.elastic.model.User;
import com.tesei7.elastic.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

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
        Project library = Project.builder().name("Library").description("Library project with books, authors, etc.")
                .startDate(toDate(LocalDate.now().minusMonths(1)))
                .endDate(toDate(LocalDate.now().plusMonths(1))).build();
        Project zoo = Project.builder().name("Zoo").description("Zoo project with animals.")
                .startDate(toDate(LocalDate.now().minusYears(1)))
                .endDate(toDate(LocalDate.now().plusMonths(1).plusDays(10))).build();
        Project linux = Project.builder().name("Linux").description("Linux kernel project.")
                .startDate(toDate(LocalDate.now().minusYears(20)))
                .endDate(toDate(LocalDate.now().plusYears(10).plusDays(7))).build();
        Project legacy = Project.builder().name("Legacy").description("Legacy project.")
                .startDate(toDate(LocalDate.now().minusYears(20)))
                .endDate(toDate(LocalDate.now().minusYears(10).plusDays(7))).build();
        return Stream.of(
                User.builder().id(1L).name("Ilia").teamName("Accounting Department").salary(25000L)
                        .projects(asList(library, zoo, linux)).build(),
                User.builder().id(2L).name("Leonid").teamName("Accounting Department").salary(12000L)
                        .projects(asList(linux)).build(),
                User.builder().id(3L).name("Anton").teamName("Finance Department").salary(22000L)
                        .projects(asList(library, zoo)).build(),
                User.builder().id(4L).name("Nikolay").teamName("Tech Department").salary(21000L)
                        .projects(asList(zoo, linux)).build(),
                User.builder().id(5L).name("Andrey").teamName("Tech Department").salary(17000L)
                        .projects(asList(zoo)).build(),
                User.builder().id(6L).name("Dmitriy").teamName("Accounting Department").salary(21000L)
                        .projects(asList(library)).build(),
                User.builder().id(7L).name("Ivan").teamName("Tech Department").salary(22000L)
                        .projects(asList(library, zoo, legacy)).build(),
                User.builder().id(8L).name("Boris").teamName("Accounting Department").salary(12000L)
                        .projects(asList(library, linux, legacy)).build())
                .collect(Collectors.toList());
    }

    private Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
