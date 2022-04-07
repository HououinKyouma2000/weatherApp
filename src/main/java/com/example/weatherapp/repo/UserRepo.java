package com.example.weatherapp.repo;

import com.example.weatherapp.dto.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User,Long> {
    @Query("Select u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);
}
