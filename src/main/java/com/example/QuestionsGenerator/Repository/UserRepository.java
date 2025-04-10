package com.example.QuestionsGenerator.Repository;
import com.example.QuestionsGenerator.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // Useful if you want to fetch user by email
}
