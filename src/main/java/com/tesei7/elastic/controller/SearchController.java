package com.tesei7.elastic.controller;

import com.tesei7.elastic.model.User;
import com.tesei7.elastic.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/rest/search")
public class SearchController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/name/{text}")
    public List<User> searchName(@PathVariable final String text) {
        return usersRepository.findByName(text);
    }

    @GetMapping("/team/{team}")
    public List<User> searchTeam(@PathVariable final String team) {
        return usersRepository.findByTeam(team);
    }

    @GetMapping("/salary/{salary}")
    public List<User> searchSalary(@PathVariable final Long salary) {
        return usersRepository.findBySalary(salary);
    }

    @GetMapping("/salary/greater/{salary}")
    public List<User> searchSalaryMoreThan(@PathVariable final Long salary) {
        return usersRepository.findSalaryGreater(salary);
    }

    @GetMapping("/salary/range/{from}/{to}")
    public List<User> searchSalaryRange(@PathVariable final Long from, @PathVariable final Long to) {
        return usersRepository.findSalaryRange(from, to);
    }

    @GetMapping("/salary/rangeHidden/{from}/{to}")
    public List<Map<String, Object>> searchSalaryHiddenRange(@PathVariable final Long from, @PathVariable final Long to) {
        return usersRepository.getSalaryRangeHidden(from, to);
    }

    @GetMapping("/all")
    public List<User> searchAll() {
        return StreamSupport.stream(usersRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @GetMapping("/all/{text}")
    public List<User> getAll(@PathVariable final String text) {
        return usersRepository.getAll(text);
    }

    @GetMapping("/projects/{project}")
    public List<User> searchProject(@PathVariable final String project) {
        return usersRepository.findByProjects(project);
    }

    @GetMapping("/projects/past/{salary}")
    public List<User> searchWithNonactiveProjectAndSalaryLess(@PathVariable final Long salary) {
        return usersRepository.findWithNonactiveProjectAndSalaryLess(salary);
    }
}
