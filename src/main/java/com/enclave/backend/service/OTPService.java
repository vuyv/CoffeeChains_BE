package com.enclave.backend.service;

import com.enclave.backend.entity.Employee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.SplittableRandom;

@Service
public class OTPService {

    @Autowired
            private EmployeeService employeeService;


    private String generateOTP(int otpLength) {
        SplittableRandom splittableRandom = new SplittableRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            sb.append(splittableRandom.nextInt(0, 10));
        }
        return sb.toString();
    }

    public void sendOtp(String phone) throws FirebaseAuthException {
        Employee employee = employeeService.getEmployeeByPhone(phone);
//        String otp = generateOTP(5);
        FirebaseAuth auth = FirebaseAuth.getInstance();
//        if(employee != null) {
            UserRecord result = FirebaseAuth.getInstance().createUser(new UserRecord.CreateRequest().setPhoneNumber(phone));

//
//            System.out.println();
//        }

    }
}
