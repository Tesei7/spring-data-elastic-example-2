package com.tesei7.elastic.repository;

import com.tesei7.elastic.model.User;

import java.util.List;

public interface UsersRepositoryCustomized {
    List<User> getAll(String text);
}
