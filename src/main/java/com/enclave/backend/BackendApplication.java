package com.enclave.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.IOException;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BackendApplication {

    public static void main(String[] args) throws IOException {
//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
//        FirebaseOptions firebaseOptions = FirebaseOptions
//                .builder()
//                .setCredentials(googleCredentials)
//                .build();
//        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions);
//        DateUtil dateUtil = new DateUtil();
//        try {
//            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-22");
//            System.out.println("--------Start " + dateUtil.startOfMonth(date));
//            System.out.println("--------End " + dateUtil.endOfMonth(date));
//        } catch (Exception e){
//            System.out.println(e);
//        }
        SpringApplication.run(BackendApplication.class, args);
    }
}


