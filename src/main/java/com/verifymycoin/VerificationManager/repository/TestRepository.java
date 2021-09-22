package com.verifymycoin.VerificationManager.repository;

import com.verifymycoin.VerificationManager.model.entity.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestRepository extends MongoRepository<Test, String> {

    List<Test> findByTitle(String title);
}
