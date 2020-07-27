package com.tinampiniari.thesisproject.data.repository;
import com.tinampiniari.thesisproject.data.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public interface TagsRepository extends JpaRepository<Tags,Long> {

    List<Tags> findAll();

    @Query(nativeQuery = true,value="SELECT id,tag_name FROM tags INNER JOIN user_tags ON user_tags.tags=tags.id where user_user_id=?")
    ArrayList<Tags> findByUserIdTags(Long userId);
}
