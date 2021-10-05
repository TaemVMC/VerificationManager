package com.verifymycoin.VerificationManager.repository;

import com.verifymycoin.VerificationManager.model.entity.Verification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationRepository extends MongoRepository<Verification, String> {

    Optional<List<Verification>> findAllByUserId(String userId);

    boolean existsByUserId(String userId);

    void deleteAllByUserId(String userId);
}
