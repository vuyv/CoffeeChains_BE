package com.enclave.backend.service;

import com.enclave.backend.dto.DiscountDTO;
import com.enclave.backend.entity.Discount;

import java.util.Date;
import java.util.List;

public interface DiscountService {
    Discount createDiscount(DiscountDTO dto);

    Discount updateDiscount(Discount discount);

    boolean isValidDiscount(String code, Date now);

    Discount getDiscountByCode(String code);

    List<Discount> getDiscounts();

    Discount getDiscountWithDate(String code, Date currentDate);
}
