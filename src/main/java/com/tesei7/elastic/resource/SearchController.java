package com.tesei7.elastic.resource;

import com.tesei7.elastic.model.User;
import com.tesei7.elastic.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    @GetMapping("/salary/{salary}")
    public List<User> searchSalary(@PathVariable final Long salary) {
        return usersRepository.findBySalary(salary);
    }

    @GetMapping("/all")
    public List<User> searchAll() {
        return StreamSupport.stream(usersRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @GetMapping("/all/{text}")
    public List<User> getAll(@PathVariable final String text) {
        return usersRepository.getAll(text);
    }
}
