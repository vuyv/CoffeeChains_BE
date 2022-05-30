package com.enclave.backend.repository;

import com.enclave.backend.entity.Material;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MaterialRepository extends JpaRepository<Material,Short> {
}
