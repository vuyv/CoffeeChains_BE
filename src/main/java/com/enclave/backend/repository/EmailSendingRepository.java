package com.enclave.backend.repository;

import com.enclave.backend.entity.EmailSending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSendingRepository extends JpaRepository<EmailSending, String> {
}
