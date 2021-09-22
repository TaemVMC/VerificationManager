package com.verifymycoin.VerificationManager.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
class ProfileMongoDBRepositoryTest {

    @Autowired()
    private ProfileMongoDBRepository profileMongoDBRepository;

    @Test
    public void printProjectData() {
        System.out.println(profileMongoDBRepository.findAll());
    }
}