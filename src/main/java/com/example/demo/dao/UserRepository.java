package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUsername(String username);

}
