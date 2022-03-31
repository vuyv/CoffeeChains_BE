package com.enclave.backend.repository;

import com.enclave.backend.entity.OrderDetail;
import com.enclave.backend.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}
