package com.example.avsoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.avsoft.entities.Blog;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Integer>{
	 Blog findByTitle(String title);
	 List<Blog> findByFlag(Boolean flag);

	 List<Blog> findByUseId(int useId);
}

