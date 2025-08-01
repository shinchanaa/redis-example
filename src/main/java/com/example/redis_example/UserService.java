package com.example.redis_example;


import com.example.redis_example.entity.User;
import com.example.redis_example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        System.out.println("Fetching from DB for id: " + id);
        return userRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @CachePut(value = "users", key = "#user.id")
    @CacheEvict(value = "users", key = "'all_users'")
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Cacheable(value = "users", key = "'all_users'")
    public List<User> getAllUsers() {
        System.out.println("Fetching all users from DB");
        return userRepository.findAll();
    }

    //@Cacheable(value = "users", key = "'all_users'", unless = "#result == null or #result.isEmpty()")

/*
    @Autowired
    private CacheManager cacheManager;

    public User updateUser(User user) {
        User saved = userRepository.save(user);
        cacheManager.getCache("users").put(user.getId(), saved);
        cacheManager.getCache("users").evict("all_users");
        return saved;
    }*/

}
