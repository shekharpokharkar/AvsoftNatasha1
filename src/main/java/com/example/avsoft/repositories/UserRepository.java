package com.example.avsoft.repositories;

import com.example.avsoft.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
    Optional<User> findByEmail(String email);
    
    @Query("SELECT u.role FROM User u WHERE u.id = :userId")
    String findRoleByUserId(@Param("userId") Integer userId);
}
