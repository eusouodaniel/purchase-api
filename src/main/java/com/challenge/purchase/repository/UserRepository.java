package com.challenge.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.purchase.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
