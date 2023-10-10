package com.example.LoginPage.dao;


import com.example.LoginPage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User,String> {
   User findByUserId(String userId);
}
