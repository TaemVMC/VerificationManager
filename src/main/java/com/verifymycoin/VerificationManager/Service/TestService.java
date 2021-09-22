package com.verifymycoin.VerificationManager.Service;

import com.verifymycoin.VerificationManager.common.RestException;
import com.verifymycoin.VerificationManager.model.entity.Test;
import com.verifymycoin.VerificationManager.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public Test getTest(String _id) {
        return testRepository.findById(_id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Not Found Test"));
    }

    public List<Test> getEventList(String title) {
        return testRepository.findByTitle(title);
    }

    public Test insertEvent(Test body) {
        return testRepository.insert(body);
    }
}
