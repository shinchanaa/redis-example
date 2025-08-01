package com.example.redis_example.repo;

import com.example.redis_example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
}