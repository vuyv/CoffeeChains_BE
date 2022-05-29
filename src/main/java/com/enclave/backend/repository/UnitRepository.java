package com.enclave.backend.repository;

import com.enclave.backend.entity.UnitConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<UnitConverter, Short> {
}
