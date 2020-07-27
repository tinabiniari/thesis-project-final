package com.tinampiniari.thesisproject.integration.news;

import com.tinampiniari.thesisproject.data.model.Tags;
import com.tinampiniari.thesisproject.data.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagsService {


    @Autowired
    TagsRepository tagsRepository;

    public List<Tags> getTags() {
        List<Tags> tags = tagsRepository.findAll();
        return tags;
    }

    public ArrayList<Tags> getTagsByUser(Long userId) {
        ArrayList<Tags> tags = tagsRepository.findByUserIdTags(userId);
        return tags;
    }


}
