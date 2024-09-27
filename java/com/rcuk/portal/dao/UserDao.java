package com.rcuk.portal.dao;

import com.rcuk.portal.domain.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends CrudRepository<User, String> {
    User findDistinctByEmail(String email);

    List<User> findAllByIdIn(List<String> ids);
}
