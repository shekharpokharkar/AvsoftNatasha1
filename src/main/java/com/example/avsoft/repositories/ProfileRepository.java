package com.example.avsoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.avsoft.entities.Profile;
import java.util.List;


public interface ProfileRepository extends JpaRepository<Profile, Integer> {

	Profile findByUserId(int userId);

}
