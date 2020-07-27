package com.tinampiniari.thesisproject.data.repository;

import com.tinampiniari.thesisproject.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT COUNT(user_id) FROM user_details WHERE user_id=?")
    Integer countByUserId(Long userId);
}
