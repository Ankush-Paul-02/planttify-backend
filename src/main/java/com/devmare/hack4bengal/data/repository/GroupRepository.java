package com.devmare.hack4bengal.data.repository;

import com.devmare.hack4bengal.data.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group, String>{

    Optional<Group> findByName(String name);
}
