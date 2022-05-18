package com.enclave.backend.api;

import com.enclave.backend.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/schedule")
public class SendEmailAPI {
    @Autowired
    private MailService mailService;

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> createSchedule() {
        return mailService.createSchedule();
    }

}
