package com.enclave.backend.api;

import com.enclave.backend.entity.DailyMailCreationRequest;
import com.enclave.backend.service.DailyMailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/dailyMail")
public class DailyMailAPI {
    @Autowired
    private DailyMailService dailyMailService;

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> subscriptionCreationHandler(
            @Valid @RequestBody(required = true) final DailyMailCreationRequest dailyMailCreationRequest) {
        return dailyMailService.subscribe(dailyMailCreationRequest);
    }
}
