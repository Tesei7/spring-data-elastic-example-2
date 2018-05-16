package com.tesei7.elastic.repository;

import com.tesei7.elastic.model.User;

import java.util.List;
import java.util.Map;

public interface UsersRepositoryCustomized {
    List<User> getAll(String text);

    List<Map<String, Object>> getSalaryRangeHidden(Long from, Long to);
}
