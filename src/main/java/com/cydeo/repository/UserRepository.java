package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    // update: with specific userName
    User findByUserName(String username);

    // delete: with specific userName
    @Transactional
    void deleteByUserName(String username);

    List<User> findByRoleDescriptionIgnoreCase(String description);

}
