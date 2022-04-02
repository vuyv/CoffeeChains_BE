package com.enclave.backend.converter;

import com.enclave.backend.dto.DiscountDTO;
import com.enclave.backend.entity.Discount;
import org.springframework.stereotype.Component;

@Component
public class DiscountConverter {
    public Discount toEntity(DiscountDTO dto) {
        Discount entity = new Discount();
        entity.setPercent(dto.getPercent());
        entity.setTitle(dto.getTitle());
        entity.setStartedAt(dto.getStartedAt());
        entity.setEndedAt(dto.getEndedAt());
        entity.setStatus(Discount.Status.UPCOMING);
        return entity;
    }
}
