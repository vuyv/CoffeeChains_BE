package com.enclave.backend.dto;

import com.enclave.backend.entity.Discount;
import lombok.Data;

import java.util.Date;

@Data
public class DiscountDTO {
    String code;
    String title;
    double percent;
    Date startedAt;
    Date endedAt;
    String discountCode;
    Discount.Status status;
}
