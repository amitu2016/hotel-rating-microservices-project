package com.amitu.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amitu.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

}
