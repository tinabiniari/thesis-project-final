package com.tinampiniari.thesisproject.data.repository;

import com.tinampiniari.thesisproject.data.model.Profile;
import com.tinampiniari.thesisproject.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM profile WHERE user_id=?")
    Profile findByUserId(Long userId);

    @Query(nativeQuery = true, value = "SELECT COUNT(user_id) FROM profile WHERE user_id=?")
    Integer countByUserId(Long userId);

}
