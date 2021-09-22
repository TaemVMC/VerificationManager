package com.verifymycoin.VerificationManager.controller;

import com.verifymycoin.VerificationManager.model.entity.Verification;
import com.verifymycoin.VerificationManager.repository.VerificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class VerificationController {

    private final VerificationRepository verificationRepository;
}
