package com.verifymycoin.VerificationManager.repository;

import com.verifymycoin.VerificationManager.model.entity.Verification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VerificationRepository extends MongoRepository<Verification, String> {

}
